package org.deiverbum.app.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.graphics.Typeface.BOLD;
import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.BRS;
import static org.deiverbum.app.utils.Constants.CSS_RED_A;
import static org.deiverbum.app.utils.Constants.CSS_RED_Z;
import static org.deiverbum.app.utils.Constants.ERR_RESPONSORIO;
import static org.deiverbum.app.utils.Constants.NBSP_4;
import static org.deiverbum.app.utils.Constants.NBSP_SALMOS;
import static org.deiverbum.app.utils.Constants.OBIEN;
import static org.deiverbum.app.utils.Constants.PADRENUESTRO;
import static org.deiverbum.app.utils.Constants.PRECES_IL;
import static org.deiverbum.app.utils.Constants.PRECES_R;
import static org.deiverbum.app.utils.Constants.RESP_A;
import static org.deiverbum.app.utils.Constants.RESP_R;
import static org.deiverbum.app.utils.Constants.RESP_V;

public final class Utils {

    public static final String LS = System.getProperty("line.separator");
    public static final String LS2 = LS + LS;
    public static final String ANT = "Ant. ";
    public static final float H3 = 1.4f;
    public static final float H2 = 1.7f;
    public static final float H4 = 1.1f;

    private static Context context;
    private static ForegroundColorSpan liturgicalRed = new ForegroundColorSpan(Color.parseColor("#A52A2A")); // from a color int
    public static final String SALUDO_OFICIO = toRed("V.") + " Señor, abre mis labios." + LS +
            toRed("R.") + " Y mi boca proclamará tu alabanza." + LS2;
    public Utils(Context context) {
        Utils.context = context;

    }


    public static SpannableStringBuilder formatTitle(String sOrigen) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sOrigen);
        ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(CharacterStyle.wrap(new StyleSpan(BOLD)), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    public static SpannableStringBuilder formatSubTitle(String sOrigen) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sOrigen);
        RelativeSizeSpan textSize = new RelativeSizeSpan(H3);
        ssb.setSpan(CharacterStyle.wrap(textSize), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(CharacterStyle.wrap(new StyleSpan(BOLD)), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }


    public static SpannableStringBuilder ssbSmallSize(SpannableStringBuilder ssb) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(0.8f);
        ssb.setSpan(CharacterStyle.wrap(smallSizeText), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    public static SpannableStringBuilder toSmallSize(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(0.8f);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;

    }

    public static SpannableStringBuilder toSmallSizes(Spanned sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(0.8f);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;
    }


    public static SpannableStringBuilder toSmallSizeRed(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(0.8f);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(liturgicalRed), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;
    }


    public static SpannableStringBuilder fromHtmlToSmallRed(String sOrigen) {
        Spanned s = fromHtml(sOrigen);
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(0.8f);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(liturgicalRed), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;

    }



    public static SpannableStringBuilder toH3(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(H3);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(new StyleSpan(BOLD)), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;

    }

    public static SpannableStringBuilder toH4(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(H4);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(new StyleSpan(BOLD)), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;

    }


    public static SpannableStringBuilder toH2(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(H2);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;

    }

    public static SpannableStringBuilder toH2Red(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(H2);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(liturgicalRed), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;
    }

    public static SpannableStringBuilder toH3Red(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(H3);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(liturgicalRed), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;
    }

    public static SpannableStringBuilder toH4Red(String sOrigen) {
        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(H4);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(CharacterStyle.wrap(liturgicalRed), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        return ssb;
    }

    public static SpannableStringBuilder toRed(String sOrigen) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sOrigen);
        ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    public static SpannableStringBuilder toRedNew(SpannableStringBuilder sOrigen) {
        sOrigen.setSpan(CharacterStyle.wrap(liturgicalRed), 0, sOrigen.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sOrigen;
    }

    public static SpannableStringBuilder ssbRed(SpannableStringBuilder ssb) {
        ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    public static SpannableStringBuilder getSaludoOficio() {
        SpannableStringBuilder ssb = new SpannableStringBuilder(formatTitle("INVOCACIÓN INICIAL"));
        ssb.append(LS2);
        SpannableStringBuilder ssbPartial = new SpannableStringBuilder("V. Señor, abre mis labios.");
        ssbPartial.append(LS);
        ssbPartial.append("R. Y mi boca proclamará tu alabanza.");
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 27, 29, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(ssbPartial);
        ssb.append(LS2);
        ssb.append(Utils.getFinSalmo());
        return ssb;
    }

    public static SpannableStringBuilder getSaludoOficioForReader() {
        StringBuilder sb = new StringBuilder();
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append("<p>Señor abre mis labios.</p>");
        ssb.append("<p>Y mi boca proclamará tu alabanza.</p>");
        ssb.append("<p>Gloria al Padre, y al Hijo, y al Espíritu Santo.</p>");
        ssb.append("<p>Como era en el principio ahora y siempre, por los siglos de los siglos. Amén.</p>");
        return (SpannableStringBuilder) fromHtml(ssb.toString());
    }


    public static SpannableStringBuilder getSaludoDiosMio() {
        SpannableStringBuilder ssb = new SpannableStringBuilder(formatTitle("INVOCACIÓN INICIAL"));
        ssb.append(LS2);
        SpannableStringBuilder ssbPartial = new SpannableStringBuilder("V. Dios mío, ven en mi auxilio.");
        ssbPartial.append(LS);
        ssbPartial.append("R. Señor, date prisa en socorrerme.");
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 32, 34, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(ssbPartial);
        ssb.append(LS2);
        ssb.append(Utils.getFinSalmo());
        return ssb;
    }

    public static SpannableStringBuilder getSaludoDiosMioForReader() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append("<p>Dios mío, ven en mi auxilio.</p>");
        ssb.append("<p>Señor, date prisa en socorrerme.</p>");
        ssb.append("<p>Gloria al Padre, y al Hijo, y al Espíritu Santo.</p>");
        ssb.append("<p>Como era en el principio ahora y siempre, por los siglos de los siglos. Amén.</p>");
        return (SpannableStringBuilder) fromHtml(ssb.toString());
    }

    public static SpannableStringBuilder getConclusionHorasMayores() {
        SpannableStringBuilder ssb = new SpannableStringBuilder(formatTitle("CONCLUSIÓN"));
        ssb.append(LS2);
        SpannableStringBuilder ssbPartial = new SpannableStringBuilder("V. El Señor nos bendiga, nos guarde de todo mal y nos lleve a la vida eterna.");
        ssbPartial.append(LS);
        ssbPartial.append("R. Amén.");
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 78, 80, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(ssbPartial);
        return ssb;
    }

    public static SpannableStringBuilder getConclusionHorasMayoresForRead() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(fromHtml("<p>El Señor nos bendiga, nos guarde de todo mal y nos lleve a la vida eterna.</p>"));
        ssb.append(fromHtml("<p>Amén.</p>"));
        return ssb;
    }


    public static SpannableStringBuilder getConclusionIntermedia() {
        SpannableStringBuilder ssb = new SpannableStringBuilder(formatTitle("CONCLUSIÓN"));
        ssb.append(LS2);
        SpannableStringBuilder ssbPartial = new SpannableStringBuilder("V. Bendigamos al Señor.");
        ssbPartial.append(LS);
        ssbPartial.append("R. Demos gracias a Dios.");
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssbPartial.setSpan(CharacterStyle.wrap(liturgicalRed), 24, 26, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(ssbPartial);
        return ssb;
    }

    public static SpannableStringBuilder getConclusionIntermediaForRead() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append("<p>Bendigamos al Señor.</p>");
        ssb.append("<p>Demos gracias a Dios.</p>");
        return (SpannableStringBuilder) fromHtml(ssb.toString());
    }


    public static SpannableStringBuilder getKyrie(int type) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        switch (type) {
            case 1:
                String text = "Yo confieso ante Dios todopoderoso " + LS +
                        "y ante vosotros, hermanos " + LS +
                        "que he pecado mucho" + LS +
                        "de pensamiento, palabra, obra y omisión:" + LS +
                        "por mi culpa, por mi culpa, por mi gran culpa." + LS2 +
                        "Por eso ruego a santa María, siempre Virgen," + LS +
                        "a los ángeles, a los santos y a vosotros, hermanos," + LS +
                        "que intercedáis por mí ante Dios, nuestro Señor.";

                ssb.append(text);

                break;
            case 2:
                ssb.append("V. Señor, ten misericordia de nosotros.");
                ssb.append(LS);
                ssb.append("R. Porque hemos pecado contra ti.");
                ssb.append(LS2);
                ssb.append("V. Muéstranos, Señor, tu misericordia.");
                ssb.append(LS);
                ssb.append("R. Y danos tu salvación.");
                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 39, 41, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 74, 76, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 113, 115, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                break;
            case 3:
                ssb.append("V. Tú que has sido enviado a sanar los corazones afligidos: Señor, ten piedad.");
                ssb.append(LS);
                ssb.append("R. Señor, ten piedad.");
                ssb.append(LS2);

                ssb.append("V. Tú que has venido a llamar a los pecadores: Cristo, ten piedad.");
                ssb.append(LS);

                ssb.append("R. Cristo, ten piedad.");
                ssb.append(LS2);

                ssb.append("V. Tú que estás sentado a la derecha del Padre para interceder por nosotros: Señor, ten piedad.");
                ssb.append(LS);

                ssb.append("R. Señor, ten piedad.");
                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 78, 80, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 100, 103, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 169, 172, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 190, 194, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 289, 292, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            default:
                ssb.append("");
                break;
        }


        return ssb;

    }


    /**
     * Método que da formato a algunos textos recibidos desde el servidor <br />
     * He creado una especie de contrato entre la API y la APP para reducir el volúmen de datos <br />
     * que se transmite y formatear el contenido de forma local. <br />
     * El reemplazo de caracteres es como sigue: <br />
     * § : salto de párrafo <br />
     * _ : salto de línea y sangría (para los salmos y algunos himnos)
     * ~ : salto de línea sin sangría
     * ¦ : DIFERENCIAR UNO CON SALTO Y OTRO SIN SALTO varios espacios en blanco (una especia de tabulador), se usa en algunas referencias de textos
     * ∞ : la rúbrica <i>Se pueden añadir algunas intenciones libres.</i>
     * ≠ : salto de línea y sangría con un guión rojo, para las preces
     * ℇ : rúbrica litúrgica "O bien"
     * † : cruz en antífonas
     * ƞ : la N. de color rojo que sustituye el nombre del Papa o del Obispo
     * Ʀ : la R./ de color rojo (responsorio)
     * Ɽ : la R. de color rojo (responsorio sin ./)
     * ⟨ : paréntesis de apertura en rojo
     * ⟩ : paréntesis de cierre en rojo
     * ã : (T. P. Aleluya.):
     * ≀ : NBSP4 + brs
     *
     * @param sOrigen El texto del salmo como es recibido del servidor (los saltos de línea vienen indicados por '_' y de párrafo por '§'
     * @return Una cadena con el salmo formateado.
     */

    public static SpannedString doFormat(String sOrigen) {
        String sFormateado;
//α β γ δ ε ϝ ϛ ζ η θ ι κ λ μ ν ξ ο π ϟ ϙ ρ σ τ υ φ χ ψ ω ϡ
        /*
        u2220: ∠ ∡ ∢ ∣ ∤ ∥ ∦ ∧ ∨ ∩ ∪ ∫ ∭ ∮ ∯ ∰ ∱ ∲ ∳ ∴ ∵ ∶ ∷ ∸ ∹ ∺ ∻ ∼ ∽ ∾ ∿

        u2240: ≀ ≁ ≂ ≃ ≄ ≅ ≆ ≇ ≈ ≉ ≊ ≋ ≌ ≍ ≎ ≏ ≐ ≑ ≒ ≓ ≔ ≕ ≖ ≗ ≘ ≙ ≚ ≛ ≜ ≝ ≞ ≟

        u2260: ≠ ≡ ≢ ≣ ≤ ≥ ≦ ≧ ≨ ≩ ≪ ≫ ≬ ≭ ≮ ≯ ≰ ≱ ≲ ≳ ≴ ≵ ≶ ≷ ≸ ≹ ≺ ≻ ≼ ≽ ≾ ≿

        u2280: ⊀ ⊁ ⊂ ⊃ ⊄ ⊅ ⊆ ⊇ ⊈ ⊉ ⊊ ⊋ ⊌ ⊍ ⊎ ⊏ ⊐ ⊑ ⊒ ⊓ ⊔ ⊕ ⊖ ⊗ ⊘ ⊙ ⊚ ⊛ ⊜ ⊝ ⊞ ⊟

        u22A0: ⊠ ⊡ ⊢ ⊣ ⊤ ⊥ ⊦ ⊧ ⊨ ⊩ ⊪ ⊫ ⊬ ⊭ ⊮ ⊯ ⊰ ⊱ ⊲ ⊳ ⊴ ⊵ ⊶ ⊷ ⊸ ⊹ ⊺ ⊻ ⊼ ⊽ ⊾ ⊿

*/
        sFormateado = sOrigen
                .replace("_", NBSP_SALMOS)
                .replace("§", BRS)
                .replace("~", BR)
                .replace("¦", NBSP_4)
                .replace("⊣", BR + NBSP_4)
                .replace("≠", PRECES_R)
                .replace("∞", PRECES_IL)
                .replace("ℇ", OBIEN)
                .replace("†", CSS_RED_A + " † " + CSS_RED_Z)
                .replace("ƞ", CSS_RED_A + " N. " + CSS_RED_Z)
                .replace("Ɽ", CSS_RED_A + " R. " + CSS_RED_Z)
                .replace("⟨", CSS_RED_A + "(" + CSS_RED_Z)
                .replace("⟩", CSS_RED_A + ")" + CSS_RED_Z)
                .replace("ⱱ", CSS_RED_A + "V/." + CSS_RED_Z)
                .replace("ⱴ", CSS_RED_A + "R/." + CSS_RED_Z)
                .replace("Ʀ", CSS_RED_A + " R/. " + CSS_RED_Z + BRS);


        return new SpannedString(sFormateado);
    }

    /**
     * Método que da formato a algunos textos recibidos desde el servidor <br />
     * He creado una especie de contrato entre la API y la APP para reducir el volúmen de datos <br />
     * que se transmite y formatear el contenido de forma local. <br />
     * El reemplazo de caracteres es como sigue: <br />
     * § : salto de párrafo <br />
     * _ : salto de línea y sangría (para los salmos y algunos himnos)
     * ~ : salto de línea sin sangría
     * ¦ : DIFERENCIAR UNO CON SALTO Y OTRO SIN SALTO varios espacios en blanco (una especia de tabulador), se usa en algunas referencias de textos
     * ∞ : la rúbrica <i>Se pueden añadir algunas intenciones libres.</i>
     * ≠ : salto de línea y sangría con un guión rojo, para las preces
     * ℇ : rúbrica litúrgica "O bien"
     * † : cruz en antífonas
     * ƞ : la N. de color rojo que sustituye el nombre del Papa o del Obispo
     * Ʀ : la R./ de color rojo (responsorio)
     * Ɽ : la R. de color rojo (responsorio sin ./)
     * ⟨ : paréntesis de apertura en rojo
     * ⟩ : paréntesis de cierre en rojo
     * ã : (T. P. Aleluya.):
     * ≀ : NBSP4 + brs
     * ∸ : Para cuando no se dice gloria
     * @param sOrigen El texto del salmo como es recibido del servidor (los saltos de línea vienen indicados por '_' y de párrafo por '§'
     * @return Una cadena con el salmo formateado.
     */

    /**
     * Método que crea las preces *** terminar descripción luego
     *
     * @param precesIntro Una matriz con las diferentes partes del responsorio. Antes de pasar el parámetro evauluar que la matriz no sea nula
     * @param precesTexto    Un valor numérico para indicar de que forma es el responsorio y actuar en consecuencia
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     */

    public static String getPreces(String precesIntro, String precesTexto) {
        String sFinal;
        String[] introArray = precesIntro.split("\\|");
        if (introArray.length == 3) {
            sFinal = introArray[0] + BRS + "<i>" + introArray[1] + "</i>" + BRS + precesTexto + BR + introArray[2];
        } else {
            sFinal = precesIntro + BRS + precesTexto;

        }
        sFinal = getFormato(sFinal);
        return sFinal;
    }


    public static String getFormato(String sOrigen) {
        String sFormateado;
//α β γ δ ε ϝ ϛ ζ η θ ι κ λ μ ν ξ ο π ϟ ϙ ρ σ τ υ φ χ ψ ω ϡ
        /*
        u2220: ∠ ∡ ∢ ∣ ∤ ∥ ∦ ∧ ∨ ∩ ∪ ∫  ∭ ∮ ∯ ∰ ∱ ∲ ∳ ∴ ∵ ∶ ∷ ∸ ∹ ∺ ∻ ∼ ∽ ∾ ∿

        u2240: ≀ ≁ ≂ ≃ ≄ ≅ ≆ ≇ ≈ ≉ ≊ ≋ ≌ ≍ ≎ ≏ ≐ ≑ ≒ ≓ ≔ ≕ ≖ ≗ ≘ ≙ ≚ ≛ ≜ ≝ ≞ ≟

        u2260: ≠ ≡ ≢ ≣ ≤ ≥ ≦ ≧ ≨ ≩ ≪ ≫ ≬ ≭ ≮ ≯ ≰ ≱ ≲ ≳ ≴ ≵ ≶ ≷ ≸ ≹ ≺ ≻ ≼ ≽ ≾ ≿

        u2280: ⊀ ⊁ ⊂ ⊃ ⊄ ⊅ ⊆ ⊇ ⊈ ⊉ ⊊ ⊋ ⊌ ⊍ ⊎ ⊏ ⊐ ⊑ ⊒ ⊓ ⊔ ⊕ ⊖ ⊗ ⊘ ⊙ ⊚ ⊛ ⊜ ⊝ ⊞ ⊟

        u22A0: ⊠ ⊡ ⊢ ⊣ ⊤ ⊥ ⊦ ⊧ ⊨ ⊩ ⊪ ⊫ ⊬ ⊭ ⊮ ⊯ ⊰ ⊱ ⊲ ⊳ ⊴ ⊵ ⊶ ⊷ ⊸ ⊹ ⊺ ⊻ ⊼ ⊽ ⊾ ⊿

*/
        sFormateado = sOrigen
                .replace("_", NBSP_SALMOS)
                .replace("§", BRS)
                .replace("~", BR)
                .replace("¦", NBSP_4)
                .replace("⊣", BR + NBSP_4)
                .replace("≠", PRECES_R)
                .replace("∞", PRECES_IL)
                .replace("ℇ", OBIEN)
                .replace("†", CSS_RED_A + " † " + CSS_RED_Z)
                .replace("ƞ", CSS_RED_A + " N. " + CSS_RED_Z)
                .replace("Ɽ", CSS_RED_A + " R. " + CSS_RED_Z)
                .replace("⟨", CSS_RED_A + "(" + CSS_RED_Z)
                .replace("⟩", CSS_RED_A + ")" + CSS_RED_Z)
                .replace("ⱱ", CSS_RED_A + "V/." + CSS_RED_Z)
                .replace("ⱴ", CSS_RED_A + "R/." + CSS_RED_Z)
                .replace("Ʀ", CSS_RED_A + " R/. " + CSS_RED_Z + BRS)
//NEW
                .replace("℟", CSS_RED_A + " ℟ " + CSS_RED_Z)
                .replace("℣", CSS_RED_A + " ℣ " + CSS_RED_Z)
                .replace("≀", BR + NBSP_4 + NBSP_4)
                .replace("~", BR)
                .replace("§", BRS)
                .replace("∸", BRS)
                .replace("¦", NBSP_4);



        return sFormateado;
    }

    //Ojo solución a fromHTML deprecated... ver: http://stackoverflow.com/questions/37904739/html-fromhtml-deprecated-in-android-n
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        String s = getFormato(html);
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(s);
        }
        return result;
    }

    public static SpannableStringBuilder fromHtmlSmall(String html) {
        String s = getFormato(html);
        //SpannableString ss=toSmallSizes(html);
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(s);
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(result);

        return toSmallSizes(ssb);
    }

    public static SpannedString toHtml(SpannedString html) {
        String h = "";
        SpannedString result = new SpannedString(html);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            h = Html.toHtml(result, Html.FROM_HTML_MODE_LEGACY);
        }
        return result;
    }

    public static SpannableStringBuilder sbHtml(SpannableStringBuilder html) {
        Spanned result;
        SpannableStringBuilder ssb = new SpannableStringBuilder(html);
        String sOrigen = html.toString();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(sOrigen, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(sOrigen);
        }
        ssb.append(result);
        return ssb;
    }

    public static String stripQuotation(String sOrigen) {
        String sFormateado;
//α β γ δ ε ϝ ϛ ζ η θ ι κ λ μ ν ξ ο π ϟ ϙ ρ σ τ υ φ χ ψ ω ϡ
        /*
        u2220: ∠ ∡ ∢ ∣ ∤ ∥ ∦ ∧ ∨ ∩ ∪ ∫  ∭ ∮ ∯ ∰ ∱ ∲ ∳ ∴ ∵ ∶ ∷ ∸ ∹ ∺ ∻ ∼ ∽ ∾ ∿

        u2240: ≀ ≁ ≂ ≃ ≄ ≅ ≆ ≇ ≈ ≉ ≊ ≋ ≌ ≍ ≎ ≏ ≐ ≑ ≒ ≓ ≔ ≕ ≖ ≗ ≘ ≙ ≚ ≛ ≜ ≝ ≞ ≟

        u2260: ≠ ≡ ≢ ≣ ≤ ≥ ≦ ≧ ≨ ≩ ≪ ≫ ≬ ≭ ≮ ≯ ≰ ≱ ≲ ≳ ≴ ≵ ≶ ≷ ≸ ≹ ≺ ≻ ≼ ≽ ≾ ≿

        u2280: ⊀ ⊁ ⊂ ⊃ ⊄ ⊅ ⊆ ⊇ ⊈ ⊉ ⊊ ⊋ ⊌ ⊍ ⊎ ⊏ ⊐ ⊑ ⊒ ⊓ ⊔ ⊕ ⊖ ⊗ ⊘ ⊙ ⊚ ⊛ ⊜ ⊝ ⊞ ⊟

        u22A0: ⊠ ⊡ ⊢ ⊣ ⊤ ⊥ ⊦ ⊧ ⊨ ⊩ ⊪ ⊫ ⊬ ⊭ ⊮ ⊯ ⊰ ⊱ ⊲ ⊳ ⊴ ⊵ ⊶ ⊷ ⊸ ⊹ ⊺ ⊻ ⊼ ⊽ ⊾ ⊿

*/
        sFormateado = sOrigen
                .replace("«", "")
                .replace("»", "")
                .replace("\"", "")
                .replace("\'", "")
                .replace("“", "")
                .replace("”", "")
                .replace("(", "")
                .replace(")", "")
                .replace("[...]", "");


        return sFormateado;
    }

    /**
     * Método que limpia la segunda parte de la antífona, en el caso del símblo †
     *
     * @param sAntifona Una cadena con el texto de la antífona
     * @return La misma cadena, pero sin el referido símbolo
     */

    public static String getAntifonaLimpia(String sAntifona) {
        return sAntifona.replace("†", "");
    }

    /**
     * Método que crea la cadena completa de un responsorio dado
     *
     * @param respArray Una matriz con las diferentes partes del responsorio. Antes de pasar el parámetro evauluar que la matriz no sea nula
     * @param nForma    Un valor numérico para indicar de que forma es el responsorio y actuar en consecuencia
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     */

    public static String getResponsorio(String[] respArray, int nForma) {
        String sResponsorio = ERR_RESPONSORIO + BR + "Tamaño del responsorio: " + respArray.length + " Código forma: " + nForma + BR;
        String codigoForma = String.valueOf(nForma);
        //String errMessage=ERR_RESPONSORIO + BR + "Tamaño del responsorio: " + respArray.length + " Código forma: " +nForma+ BR;
        switch (nForma) {
// Modificar los case, usando el modelo: 6001230
            case 1:
                if (respArray.length == 3) {
                    sResponsorio =
                            RESP_R + respArray[0] + RESP_A + respArray[1] + BRS +
                                    RESP_V + respArray[2] + BRS +
                                    RESP_R + Character.toUpperCase(respArray[1].charAt(0)) + respArray[1].substring(1);
                }
                break;

            case 2:
                sResponsorio =
                        RESP_R + respArray[0] + RESP_A + respArray[1] + BRS +
                                RESP_V + respArray[2] + BRS +
                                RESP_R + Character.toUpperCase(respArray[1].charAt(0)) + respArray[1].substring(1);

                break;


            case 6001230:
                //6 partes distribuidas así: 0-0-1-2-3-0, de ahí el código 6001230... si no, imposible entenderse
                // Suele ser el modelo de responsorio para Laudes
                if (respArray.length == 4) {

                    sResponsorio =
                            RESP_V + respArray[0] + BR +
                                    RESP_R + respArray[0] + BRS +
                                    RESP_V + respArray[1] + BR +
                                    RESP_R + respArray[2] + BRS +
                                    RESP_V + respArray[3] + BR +
                                    RESP_R + respArray[0] + BRS;
                }
                break;


            case 6001020:
                //6 partes distribuidas así: 0-0-1-0-2-0, de ahí el código 6001230... si no, imposible entenderse
                // Suele ser el modelo de responsorio para Laudes
                if (respArray.length == 3) {

                    sResponsorio =
                            RESP_V + respArray[0] + BR +
                                    RESP_R + respArray[0] + BRS +
                                    RESP_V + respArray[1] + BR +
                                    RESP_R + respArray[0] + BRS +
                                    RESP_V + respArray[2] + BR +
                                    RESP_R + respArray[0] + BRS;
                }
                break;


            case 4:
                sResponsorio =
                        RESP_V + respArray[0] + BR +
                                RESP_R + respArray[0] + BRS +
                                RESP_V + respArray[1] + BR +
                                RESP_R + respArray[0] + BRS +
                                RESP_V + respArray[2] + BR +
                                RESP_R + respArray[0] + BRS;
                break;

            case 201:
                sResponsorio =
                        RESP_V + respArray[0] + BR +
                                RESP_R + respArray[1] + BRS;
                break;


            default:
                //sResponsorio = ERR_RESPONSORIO + BRS + "Error " + respArray.length + " de responsorio en la fecha: " + BRS;
                break;
        }
        return sResponsorio;


    }


    /**
     * Método que crea la cadena completa de un responsorio dado
     *
     * @param respArray Una matriz con las diferentes partes del responsorio. Antes de pasar el parámetro evauluar que la matriz no sea nula
     * @param nForma    Un valor numérico para indicar de que forma es el responsorio y actuar en consecuencia
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     */

    public static String getResponsorioForReader(String[] respArray, int nForma) {
        String sResponsorio = ERR_RESPONSORIO + BR + "Tamaño del responsorio: " + respArray.length + " Código forma: " + nForma + BR;
        String codigoForma = String.valueOf(nForma);
        switch (nForma) {
            case 1:
                if (respArray.length == 3) {
                    sResponsorio =
                            respArray[0] + respArray[1] + BRS +
                                    respArray[2] + BRS +
                                    Character.toUpperCase(respArray[1].charAt(0)) + respArray[1].substring(1);
                }
                break;

            case 2:
                sResponsorio =
                        respArray[0] + respArray[1] + BRS +
                                respArray[2] + BRS +
                                Character.toUpperCase(respArray[1].charAt(0)) + respArray[1].substring(1);

                break;


            case 6001230:

                if (respArray.length == 4) {

                    sResponsorio =
                            respArray[0] + BR +
                                    respArray[0] + BRS +
                                    respArray[1] + BR +
                                    respArray[2] + BRS +
                                    respArray[3] + BR +
                                    respArray[0] + BRS;
                }
                break;


            case 6001020:

                if (respArray.length == 3) {

                    sResponsorio =
                            respArray[0] + BR +
                                    respArray[0] + BRS +
                                    respArray[1] + BR +
                                    respArray[0] + BRS +
                                    respArray[2] + BR +
                                    respArray[0] + BRS;
                }
                break;


            case 4:
                sResponsorio =
                        respArray[0] + BR +
                                respArray[0] + BRS +
                                respArray[1] + BR +
                                respArray[0] + BRS +
                                respArray[2] + BR +
                                respArray[0] + BRS;
                break;

            case 201:
                sResponsorio =
                        respArray[0] + BR +
                                respArray[1] + BRS;
                break;

            default:
                break;
        }
        return sResponsorio;


    }

    public static final Spanned getTeDeum() {
        String teDeum = "<p>Señor, Dios eterno, alegres te cantamos, <br />a ti nuestra alabanza, <br />a ti, Padre del cielo, te aclama la creación. <br /><br />Postrados ante ti, los ángeles te adoran <br />y cantan sin cesar: <br /><br />Santo, santo, santo es el Señor, <br />Dios del universo; <br />llenos están el cielo y la tierra de tu gloria. <br /><br />A ti, Señor, te alaba el coro celestial de los apóstoles, <br />la multitud de los profetas te enaltece, <br />y el ejército glorioso de los mártires te aclama. <br /><br />A ti la Iglesia santa, <br />por todos los confines extendida, <br />con júbilo te adora y canta tu grandeza: <br /><br />Padre, infinitamente santo, <br />Hijo eterno, unigénito de Dios, <br />Santo Espíritu de amor y de consuelo. <br /><br />Oh Cristo, tú eres el Rey de la gloria, <br />tú el Hijo y Palabra del Padre, <br />tú el Rey de toda la creación. <br /><br />Tú, para salvar al hombre, <br />tomaste la condición de esclavo <br />en el seno de una virgen. <br /><br />Tú destruiste la muerte <br />y abriste a los creyentes las puertas de la gloria. <br /><br />Tú vives ahora, <br />inmortal y glorioso, en el reino del Padre. <br /><br />Tú vendrás algún día, <br />como juez universal. <br /><br />Muéstrate, pues, amigo y defensor <br />de los hombres que salvaste. <br /><br />Y recíbelos por siempre allá en tu reino, <br />con tus santos y elegidos. <br /><br />Salva a tu pueblo, Señor, <br />y bendice a tu heredad. <br /><br />Sé su pastor, <br />y guíalos por siempre. <br /><br />Día tras día te bendeciremos <br />y alabaremos tu nombre por siempre jamás. <br /><br />Dígnate, Señor, <br />guardarnos de pecado en este día. <br /><br />Ten piedad de nosotros, Señor, <br />ten piedad de nosotros. <br /><br />Que tu misericordia, Señor, venga sobre nosotros, <br />como lo esperamos de ti. <br /><br />A ti, Señor, me acojo, <br />no quede yo nunca defraudado.</p>";
        return fromHtml(teDeum);
    }


    public static final SpannableStringBuilder getNoGloria() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("No se dice Gloria");
        return toRedNew(ssb);
    }

    public static final Spanned getFinSalmo() {
        String fin = "Gloria al Padre, y al Hijo, y al Espíritu Santo." + BR
                + NBSP_SALMOS + "Como era en el principio ahora y siempre, "
                + NBSP_SALMOS + "por los siglos de los siglos. Amén.";
        return fromHtml(fin);
    }

    public static final Spanned getFinSalmoForRead() {
        String fin = "<p>Gloria al Padre, y al Hijo, y al Espíritu Santo.<br />" +
                "Como era en el principio ahora y siempre, "
                + "por los siglos de los siglos. Amén.</p>";
        return fromHtml(fin);
    }

    public static final Spanned getPadreNuestro() {

        return fromHtml(PADRENUESTRO);
    }

    /**
     * Método que devuelve la fecha del sistema
     *
     * @return Una cadena con la fecha
     */

    public static String getHoy() {
        Date newDate = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return format.format(new Date());
    }

    /**
     * Método que devuelve la fecha del sistema en forma legible
     *
     * @return Una cadena con la fecha
     */


    public static String getFecha() {
        Date newDate = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("dd 'de' MMMM yyyy", Locale.getDefault());
        return format.format(new Date());
    }


    /**
     * Método que devuelve la fecha del sistema en forma MMDD para santoral
     *
     * @return Una cadena con la fecha
     */

    public static String getHoyYYYYMM() {
        Date newDate = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM", Locale.getDefault());
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return format.format(new Date());
    }

    public static int getHoyDD() {
        Date newDate = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("dd", Locale.getDefault());
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
        //return LocalDate.now().getDayOfWeek().ordinal();
        //return format.format(new Date());
    }

    public static String getLongDate(String dateString) {
        SimpleDateFormat longFormat =
                new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String mLongDate = "";
        Date mDate = null;
        try {
            mDate = df.parse(dateString);
            mLongDate = longFormat.format(mDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mLongDate;
    }

}
