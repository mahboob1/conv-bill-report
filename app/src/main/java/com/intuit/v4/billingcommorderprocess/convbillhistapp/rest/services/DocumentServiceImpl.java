package com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.services;

import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.InvoicePayload;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {

    private static final String UK_HEADER_URL = "http://content.intuit.co.uk/subuk/header_045137.jpg";
    private static final String UK_FOOTER_URL = "http://content.intuit.co.uk/subuk/footer_045137.jpg";

    private final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Autowired
    private TemplateEngine templateEngine;

    private String caHeader;
    private String ukHeader;
    private String ukFooter;

    @PostConstruct
    public void init() throws IOException {
        caHeader = getImageData("images/header_ca.png");
        ukHeader = getImageData("images/header_uk.png");
        ukFooter = getImageData("images/footer_uk.png");
    }

    @Override
    public String ukHtmlToBase64Pdf(String html) {
        return htmlToBase64Pdf(fixUkImages(html));
    }

    @Override
    public String caXmlToBase64Pdf(String xml, Long paymentNumber, String templateName) {
        InvoicePayload payload = unmarshalInvoicePayload(xml);
        InvoicePayload.Recipient recipient = getTargetRecipient(payload.getRecipients(), paymentNumber);
        String html = templateEngine.process(templateName, buildTemplateContext(recipient));
        return htmlToBase64Pdf(html);
    }

    public String htmlToBase64Pdf(String html) {
        String xhtml = htmlToXhtml(html);
        byte[] pdf = xhtmlToPdf(xhtml);
        return base64Encode(pdf);
    }

    private String fixUkImages(String html) {
        String result = html.replace(UK_HEADER_URL, ukHeader);
        result = result.replace(UK_FOOTER_URL, ukFooter);
        return result;
    }

    private String htmlToXhtml(String html) {
        Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }

    private byte[] xhtmlToPdf(String xhtml) {
        ITextRenderer iTextRenderer = new ITextRenderer();
        iTextRenderer.setDocumentFromString(xhtml);
        iTextRenderer.layout();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        iTextRenderer.createPDF(os);
        return os.toByteArray();
    }

    private String base64Encode(byte[] bytes) {
        byte[] encoded = Base64.getEncoder().encode(bytes);
        return new String(encoded);
    }

    private String getImageData(String imagePath) throws IOException {
        ClassPathResource imgFile = new ClassPathResource(imagePath);
        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
        return "data:image/png;base64," + base64Encode(bytes);
    }

    private InvoicePayload unmarshalInvoicePayload(String xml) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(InvoicePayload.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (InvoicePayload) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            logger.error("Error happened while unmarshalling xml payload", e);
            throw new RuntimeException(e);
        }
    }

    private InvoicePayload.Recipient getTargetRecipient(List<InvoicePayload.Recipient> recipients, Long paymentNumber) {
        Optional<InvoicePayload.Recipient> targetRecipient = recipients.stream().filter(recipient -> {
            Optional<InvoicePayload.Property> paymentNumberProp =
                    recipient.getProperties().stream().filter(property ->
                            property.getName().equals("PAYMENT_NUMBER")).findFirst();
            return paymentNumberProp.isPresent() &&
                    paymentNumber.toString().equals(paymentNumberProp.get().getValue());
        }).findFirst();
        return targetRecipient.orElse(null);
    }

    private Context buildTemplateContext(InvoicePayload.Recipient recipient) {
        Context context = new Context();
        recipient.getProperties().forEach(property -> context.setVariable(property.getName(), property.getValue()));
        context.setVariable("headerImage", caHeader);
        return context;
    }
}
