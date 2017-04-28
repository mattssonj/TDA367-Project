package corp.skaj.foretagskvitton.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.provider.CalendarContract.CalendarCache.URI;

/**
 *
 */
public class TextCollector {

    private TextCollector() {
    }

    /**
     * This method collects Strings from image.
     * @param context
     * @param uri
     * @return listOfStrings
     * @throws IOException
     */
    public static List<String> collectStringsFromImage(Context context, Uri uri) throws IOException {
        Bitmap bmp = createImageFromUri(context, uri);
        SparseArray<TextBlock> textBlocks = getTextBlocksFromImage(context, bmp);

        if (textBlocks == null) {
            // TODO error handling here, no text was found or textrecognizer is not working
        }
        return buildListOfStrings(textBlocks);
    }

    /**
     * This method converts an Uri to a Bitmap.
     * @param context
     * @param uri
     * @return Bitmap
     * @throws IOException
     */
    private static Bitmap createImageFromUri(Context context, Uri uri) throws IOException {
        if (URI == null) {
            throw new NullPointerException("URI is null");
        }
        Bitmap bmp = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        return bmp;
    }

    /**
     * This method collects Textblocks from an image.
     * @param context
     * @param bmp
     * @return listOfTextBlocks if TextRecognizer is operational. Null otherwise
     */
    private static SparseArray<TextBlock> getTextBlocksFromImage(Context context, Bitmap bmp) {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(context.getApplicationContext()).build();

        if (textRecognizer.isOperational()) {
            Frame frame = new Frame.Builder().setBitmap(bmp).build();
            SparseArray<TextBlock> listOfTextBlocks = textRecognizer.detect(frame);
            textRecognizer.release();
            return listOfTextBlocks;
        } else {
            System.out.println("TextRecognizer is not operational");
            // TODO some kind of error handling here?
            return null;
        }
    }

    /**
     * This method collects Strings from Textblocks.
     * @param listOfTextBlock
     * @return listOfStrings
     */
    private static List<String> buildListOfStrings(SparseArray<TextBlock> listOfTextBlock) {
        List<String> listOfStrings = new ArrayList<>();

        for (int i = 0; i < listOfTextBlock.size(); i++) {
            if (listOfTextBlock.get(i) != null) {
                listOfStrings.addAll(linesToStrings(listOfTextBlock.get(i).getComponents()));
            }
        }
        return listOfStrings;
    }

    /**
     * This method collects lines from Textblocks.
     * @param list
     * @return listOfStrings
     */
    private static List<String> linesToStrings(List<? extends Text> list) {
        List<String> listOfStrings = new ArrayList<>();

        for (Text t : list) {
            listOfStrings.add(t.getValue());
        }
        return listOfStrings;
    }
}