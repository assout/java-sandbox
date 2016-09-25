package com.xxx.yyy.zzz.doclets;

import java.awt.print.Book;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBXMLHandler {
 
    public static void marshal(Book book, File selectedFile)
            throws IOException, JAXBException {
        JAXBContext context;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(selectedFile));
            context = JAXBContext.newInstance(Book.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            m.setProperty("com.sun.xml.internal.bind.characterEscapeHandler",
//                new CharacterEscapeHandler() {
//                    @Override
//                    public void escape(char[] ch, int start, int length,
//                            boolean isAttVal, Writer writer)
//                            throws IOException {
//                        writer.write(ch, start, length);
//                    }
//                });
            m.marshal(book, writer);
        } finally {
            try {
                writer.close();
            } catch (IOException io) { /* ignore */
            }
        }
    }
 
    public static Book unmarshal(File importFile) throws JAXBException {
        Book book = new Book();
        JAXBContext context;
 
        context = JAXBContext.newInstance(Book.class);
        Unmarshaller um = context.createUnmarshaller();
        book = (Book) um.unmarshal(importFile);
 
        return book;
    }
}
