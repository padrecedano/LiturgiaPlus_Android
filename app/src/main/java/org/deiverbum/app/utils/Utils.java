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

    //private static int opaqueRed2 = Color.valueOf("0xffff0000"); // from a color int
//private Context c=getApplicationContext();
    public Utils(Context context) {
        Utils.context = context;
        //stringBuilder = new StringBuilder();
        //spanSections = new ArrayList<>();
        //relativeSmallSpan = new RelativeSizeSpan(0.8f);
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
        //SpannableStringBuilder ssb = new SpannableStringBuilder("");
        //SpannableString spannableString = new SpannableString(sOrigen);
        ssb.setSpan(CharacterStyle.wrap(smallSizeText), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //ssb.append(spannableString);
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
//        Typeface tf = ResourcesCompat.getFont(context, R.font.roboto_black);
        //Typeface robotoRegular = Typeface.createFromAsset(ResourcesCompat.getAssets(), "fonts/Roboto-Regular.ttf");

        RelativeSizeSpan smallSizeText = new RelativeSizeSpan(H2);
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        SpannableString spannableString = new SpannableString(sOrigen);
        spannableString.setSpan(CharacterStyle.wrap(smallSizeText), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //       spannableString.setSpan(tf, 0,spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

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

    public static SpannableStringBuilder toRed(String sOrigen) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(sOrigen);
        ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;

    }

    public static SpannableStringBuilder toRedNew(SpannableStringBuilder sOrigen) {
        //SpannableStringBuilder ssb = new SpannableStringBuilder(sOrigen);
        sOrigen.setSpan(CharacterStyle.wrap(liturgicalRed), 0, sOrigen.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sOrigen;

    }

    public static SpannableStringBuilder ssbRed(SpannableStringBuilder ssb) {
        //SpannableStringBuilder ssb = new SpannableStringBuilder(sOrigen);
        ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;

    }

    public static SpannableStringBuilder getSaludoOficio() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("V. Señor, abre mis labios.");
        ssb.append(LS);
        ssb.append("R. Y mi boca proclamará tu alabanza.");
        ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(CharacterStyle.wrap(liturgicalRed), 27, 29, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
                .replace("Ʀ", CSS_RED_A + " R/. " + CSS_RED_Z + BRS);


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

    public static String getResponsorio(String respArray[], int nForma) {
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

            case 31:
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

    public static String getResponsorioForReader(String respArray[], int nForma) {
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

            case 31:
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

    public static final Spanned getPadreNuestro() {

        return fromHtml(PADRENUESTRO);
    }

    public static String getSaludoOficioForReader() {
        return "Señor abre mis labios. Y mi boca proclamará tu alabanza.";
    }
}
