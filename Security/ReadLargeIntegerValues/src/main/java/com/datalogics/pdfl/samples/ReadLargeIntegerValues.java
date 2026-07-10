package com.datalogics.pdfl.samples;

import com.datalogics.PDFL.*;

/*
 *
 * This sample program demonstrates reading PDF integer values that exceed the
 * signed 32-bit range (2^31-1 = 2,147,483,647). The legacy 32-bit accessor
 * PDFInteger.getValue() truncates such values; the 64-bit accessor
 * PDFInteger.getLongValue() returns them intact.
 *
 * The motivating case is signature /ByteRange offsets in a document larger than
 * 2 GB. The "Compare incremental revisions" (DocMDP) feature in
 * PDFObjectExplorerEx reads those offsets to locate the signed-revision
 * boundary; an offset read through the 32-bit accessor wraps to a negative
 * number and the boundary is lost.
 *
 * Copyright (c) 2026, Datalogics, Inc. All rights reserved.
 *
 */
public class ReadLargeIntegerValues {

    /**
     * @param args
     */
    public static void main(String[] args) throws Throwable {
        System.out.println("ReadLargeIntegerValues sample:");

        Library lib = new Library();
        try {
            // Match PDFObjectExplorerEx: allow XFA documents to be opened.
            lib.setAllowOpeningXFA(true);
            System.out.println("Initialized the library.");

            // Default to the crafted test document; override with args[0].
            String sInput = "C:\\Users\\saharay\\Desktop\\gap2-bigint-test.pdf";
            if (args.length > 0)
                sInput = args[0];

            System.out.println("Input file: " + sInput);

            Document doc = new Document(sInput);
            try {
                PDFDict root = doc.getRoot();

                // A plain catalog integer above 2^31: /BigInt 5000000000.
                PDFObject bigObj = root.get("BigInt");
                if (bigObj instanceof PDFInteger) {
                    PDFInteger bigInt = (PDFInteger) bigObj;
                    System.out.println();
                    System.out.println("/BigInt");
                    System.out.println("  getValue()     (32-bit) = " + bigInt.getValue() + "   <- truncated");
                    System.out.println("  getLongValue() (64-bit) = " + bigInt.getLongValue() + "   (expected 5000000000)");
                }

                // The DocMDP use case: signature /ByteRange offsets > 2^31,
                // reached the same way the revision comparer does:
                // Root -> AcroForm -> Fields[0] -> V -> ByteRange.
                PDFArray byteRange = null;
                PDFObject acro = root.get("AcroForm");
                if (acro instanceof PDFDict) {
                    PDFObject fields = ((PDFDict) acro).get("Fields");
                    if (fields instanceof PDFArray && ((PDFArray) fields).getLength() > 0) {
                        PDFObject field = ((PDFArray) fields).get(0);
                        if (field instanceof PDFDict) {
                            PDFObject v = ((PDFDict) field).get("V");
                            if (v instanceof PDFDict) {
                                PDFObject b = ((PDFDict) v).get("ByteRange");
                                if (b instanceof PDFArray) byteRange = (PDFArray) b;
                            }
                        }
                    }
                }
                if (byteRange != null) {
                    StringBuilder legacy = new StringBuilder("  getValue()     (32-bit) = [");
                    StringBuilder full = new StringBuilder("  getLongValue() (64-bit) = [");
                    for (int i = 0; i < byteRange.getLength(); i++) {
                        PDFInteger pi = (PDFInteger) byteRange.get(i);
                        legacy.append(i > 0 ? " " : "").append(pi.getValue());
                        full.append(i > 0 ? " " : "").append(pi.getLongValue());
                    }
                    legacy.append("]   <- the two > 2 GB offsets wrap negative");
                    full.append("]   (expected [0 3221225472 3221237817 5000])");
                    System.out.println();
                    System.out.println("Signature /ByteRange");
                    System.out.println(legacy);
                    System.out.println(full);
                }
            } finally {
                doc.delete();
            }
        } finally {
            lib.delete();
        }
    }
}
