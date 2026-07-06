package com.datalogics.pdfl.samples;

import java.util.*;

import com.datalogics.PDFL.*;

/*
 *
 * This sample program demonstrates the use of Document.getRolledBackDocuments()
 * to enumerate the prior incremental revisions of a PDF and materialize each one
 * to its own file. This is the same technique the PDFObjectExplorerEx "Compare
 * incremental revisions" feature uses: enumerate the revisions, save() each one
 * to a standalone file, then hand the resulting files to a compare engine.
 *
 * Copyright (c) 2026, Datalogics, Inc. All rights reserved.
 *
 */
public class GetRolledBackDocuments {

    /**
     * @param args
     */
    public static void main(String[] args) throws Throwable {
        System.out.println("GetRolledBackDocuments sample:");

        Library lib = new Library();
        try {
            // Match PDFObjectExplorerEx: allow XFA documents to be opened.
            lib.setAllowOpeningXFA(true);
            System.out.println("Initialized the library.");

            // Default to an incrementally-saved test document; override with args[0].
            String sInput = "C:\\Users\\saharay\\Desktop\\1TgHQ1_article9.pdf";
            if (args.length > 0)
                sInput = args[0];

            System.out.println("Input file: " + sInput);

            Document doc = new Document(sInput);
            try {
                System.out.println("Current document has " + doc.getNumPages() + " page(s).");

                // Enumerate the prior incremental revisions of the document.
                List<RolledBackDocument> revisions = doc.getRolledBackDocuments();

                if (revisions == null || revisions.isEmpty()) {
                    System.out.println("No prior incremental revisions found.");
                    return;
                }

                System.out.println("Found " + revisions.size() + " prior incremental revision(s).");

                // Materialize each revision to its own file and report on it.
                // This mirrors ResolveCompareFiles() in the C# PDFObjectExplorerEx,
                // which writes each chosen revision out before comparing.
                for (int i = 0; i < revisions.size(); i++) {
                    String revPath = "article9-revision-" + i + ".pdf";
                    revisions.get(i).save(revPath);
                    System.out.println();
                    System.out.println("Revision " + i + " saved to: " + revPath);

                    // Re-open the saved revision as an ordinary Document to prove it
                    // is a usable, standalone PDF (what the compare engine consumes).
                    Document revDoc = new Document(revPath);
                    try {
                        int revPages = revDoc.getNumPages();
                        System.out.println("  Revision " + i + " page count: " + revPages
                                + "  (delta vs current: " + (doc.getNumPages() - revPages) + ")");
                    } finally {
                        revDoc.delete();
                    }

                    // NOTE: do not call revisions.get(i).closeDocument() here. The
                    // RolledBackDocument instances are owned by the parent Document
                    // and are released when it is disposed; closing them explicitly
                    // double-frees and can crash the JVM on exit.
                }

                System.out.println();
                System.out.println("Done. Each revision file can now be diffed against the current document.");
            } finally {
                doc.delete();
            }
        } finally {
            lib.delete();
        }
    }
}
