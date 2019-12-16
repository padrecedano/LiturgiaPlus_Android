package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class CanticoEvangelico {
    private String antifona;
    private String magnificat;
    private String benedictus;
    private String texto;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setMagnificat(String magnificat) {
        this.magnificat = magnificat;
    }
    public SpannableStringBuilder getAntifona() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");

        String preAnt = "Ant. ";
        ssb.append(Utils.toRed(preAnt));
        ssb.append(antifona);
        return ssb;
    }

    public void setAntifona(String antifona) {
        this.antifona = antifona;
    }

    public Spanned getBenedictus() {
        //SpannableStringBuilder ce=new SpannableStringBuilder("Bendito sea el Señor, Dios de Israel,_porque ha visitado y redimido a su pueblo,_suscitándonos una fuerza de salvación_en la casa de David, su siervo,_según lo había predicho desde antiguo_por boca de sus santos profetas.§Es la salvación que nos libra de nuestros enemigos_y de la mano de todos los que nos odian;_ha realizado así la misericordia que tuvo con nuestros padres,_recordando su santa alianza_y el juramento que juró a nuestro padre Abraham.§Para concedernos que, libres de temor,_arrancados de la mano de los enemigos,_le sirvamos con santidad y justicia,_en su presencia, todos nuestros días.§Y a ti, niño, te llamarán profeta del Altísimo,_porque irás delante del Señor_a preparar sus caminos,_anunciando a su pueblo la salvación,_el perdón de sus pecados.§Por la entrañable misericordia de nuestro Dios,_nos visitará el sol que nace de lo alto,_para iluminar a los que viven en tiniebla_y en sombra de muerte,_para guiar nuestros pasos_por el camino de la paz.");
        String ss = "Bendito sea el Señor, Dios de Israel,_porque ha visitado y redimido a su pueblo,_suscitándonos una fuerza de salvación_en la casa de David, su siervo,_según lo había predicho desde antiguo_por boca de sus santos profetas.§Es la salvación que nos libra de nuestros enemigos_y de la mano de todos los que nos odian;_ha realizado así la misericordia que tuvo con nuestros padres,_recordando su santa alianza_y el juramento que juró a nuestro padre Abraham.§Para concedernos que, libres de temor,_arrancados de la mano de los enemigos,_le sirvamos con santidad y justicia,_en su presencia, todos nuestros días.§Y a ti, niño, te llamarán profeta del Altísimo,_porque irás delante del Señor_a preparar sus caminos,_anunciando a su pueblo la salvación,_el perdón de sus pecados.§Por la entrañable misericordia de nuestro Dios,_nos visitará el sol que nace de lo alto,_para iluminar a los que viven en tiniebla_y en sombra de muerte,_para guiar nuestros pasos_por el camino de la paz.";

        //String s=Utils.getFormato(ss);
        Spanned sp = Utils.fromHtml(ss);
        return Utils.fromHtml(ss);//Utils.getFormato(ss);

    }


    public void setBenedictus(String texto) {
        this.benedictus = benedictus;
    }

    public Spanned getMagnificat() {

        String magnificat = new StringBuilder().append("Proclama mi alma la grandeza del Señor,_se alegra mi espíritu en Dios, mi salvador;_porque ha mirado la humillación de su esclava.§Desde ahora me felicitarán todas las generaciones,_porque el Poderoso ha hecho obras grandes por mí:_su nombre es santo,_y su misericordia llega a sus fieles_de generación en generación.§Él hace proezas con su brazo:_dispersa a los soberbios de corazón,_derriba del trono a los poderosos y enaltece a los humildes,_a los hambrientos los colma de bienes_y a los ricos los despide vacíos.§Auxilia a Israel, su siervo,_acordándose de la misericordia_—como lo había prometido a nuestros padres—_en favor de Abrahán y su descendencia por siempre.").toString();
        return Utils.fromHtml(magnificat);
    }

    public SpannableStringBuilder getHeader() {

        return Utils.formatTitle("CÁNTICO EVANGÉLICO");
    }

    public SpannableStringBuilder getAntifonaForRead() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        ssb.append(antifona);
        return ssb;
    }

}
