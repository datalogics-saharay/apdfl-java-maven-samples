![Datalogics Adobe PDF Library](https://raw.github.com/datalogics/dl-icons/develop/DLBanner_Nuget.png)

[Documentation](https://docs.datalogics.com/apdfl18/Java/index.html) &nbsp; | &nbsp; [Support](https://www.datalogics.com/tech-support-pdfs/) &nbsp; | &nbsp; [Release Notes](https://docs.datalogics.com/apdfl18/Release_Notes.html) &nbsp; | &nbsp;[Homepage](https://www.datalogics.com)

[![Download a Free Trial](https://img.shields.io/badge/maven%20package-APDFL%20Free%20Trial-blue?style=plastic&logo=apachemaven)](https://central.sonatype.com/artifact/com.datalogics.pdfl/pdfl)

## Adobe PDF Library
Built upon Adobe source code used for Acrobat, Datalogics Adobe PDF Library SDK provides stable, reliable code and the flexibility to develop with Java, C#, VB (VB.NET), or C++. APDFL is the most complete SDK for PDF creation, manipulation and management. Best for enterprise/larger organizations of developers and independent software vendors (ISVs) who need to incorporate Adobe's PDF functionality into their own internal or external applications.

Live support from PDF development experts.

Licensing and Pricing options are customized to your usage and requirements. For OEM and SaaS customers we will provide you with a non-license managed software package for easier distribution embedded within your applications.

## Free trial & license activation

To activate the free trial:
1. Visit https://www.datalogics.com/pdf-sdk-free-trial to obtain an activation key.
2. A prompt will appear on your console when executing Datalogics sample code.

Alternatively, to use an activation key in code, the <em>setLicenseKey()</em> method of the <em>Library</em> class can be set to
a valid activation key <b>prior</b> to instantiating the library.
```
    Library.setLicenseKey("xxxx-xxxx-xxxx-xxxx");
    Library lib = new Library();
```


## Extensive PDF Capabilities
- ![NewFeature](https://img.shields.io/badge/New!-blue?style=plastic) ![Windows](https://img.shields.io/badge/Only-blue?style=plastic&logo=windows&labelColor=blue) Conversion of PDF to Microsoft Office Word, Excel, and PowerPoint (.docx, .xlsx, .pptx) documents.
- Create and edit annotations
- Content creation and editing
- Color management and conversion
- Extraction of text, images, forms
- Redaction
- Content modification - merge, split, flatten, layers
- Compression / optimization / linearization for Fast Web View
- Display, Render, Print
- Conversion to PDF/A, PDF/X, EPS, PostScript, XPS, Factur-X, ZUGFeRD, image formats (JPG, TIFF, PNG, BMP), Office formats (docx, xlsx, pptx)
- Forms - Import, export, flatten, AcroForms
- Image manipulation and conversion
- OCR
- Support for Optional Content Groups and PDF Layers
- Printing
- Security - Password, encrypt, watermark
- Search for and replace or edit text

## Running the Samples
**For 64-bit Intel Windows, 64-bit Intel Linux, or Mac ARM systems:**
- Clone this repository to your system
- Select a sample you would like to try
- Open the folder containing the sample in [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- From the Maven tab in the right sidebar, run the "Compile" phase
- Run the sample

**For other systems:**
See [Using APDFL and Maven on other platforms](using_on_other_platforms.md)
