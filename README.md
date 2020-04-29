# ImpressExtractNotes
**"Just the notes, ma'am!"**

This command-line program serves to extract the presenter Notes from a Libre Office Impress presentation. This gets **JUST THE NOTES** in a simple text file; this does *not* deal with the Notes Slides Pages in any way. 

To use this program first Export your presentation as a single-document HTML file. Then, run this program on it: **java ImpressExtractNotes infile outfile**. This retrieves the slide titles and the Notes text from any slide that has such annotation. Use this as a printed script in cases where your presentation is being delivered to the audience via a single-monitor channel (including on a laptop, classroom that lacks a second monitor, videoconferencing/distance learning platform, etc.)

This was requested as a feature on the Libre Office Bugzilla site, but was rejected, hence this standalone utility: see https://bugs.documentfoundation.org/show_bug.cgi?id=132337
