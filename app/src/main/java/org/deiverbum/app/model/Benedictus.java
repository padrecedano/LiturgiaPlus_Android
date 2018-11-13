package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Benedictus {
    private String antifona;
    private String texto;

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

    public Spanned getTexto() {
        //SpannableStringBuilder ce=new SpannableStringBuilder("Bendito sea el Señor, Dios de Israel,_porque ha visitado y redimido a su pueblo,_suscitándonos una fuerza de salvación_en la casa de David, su siervo,_según lo había predicho desde antiguo_por boca de sus santos profetas.§Es la salvación que nos libra de nuestros enemigos_y de la mano de todos los que nos odian;_ha realizado así la misericordia que tuvo con nuestros padres,_recordando su santa alianza_y el juramento que juró a nuestro padre Abraham.§Para concedernos que, libres de temor,_arrancados de la mano de los enemigos,_le sirvamos con santidad y justicia,_en su presencia, todos nuestros días.§Y a ti, niño, te llamarán profeta del Altísimo,_porque irás delante del Señor_a preparar sus caminos,_anunciando a su pueblo la salvación,_el perdón de sus pecados.§Por la entrañable misericordia de nuestro Dios,_nos visitará el sol que nace de lo alto,_para iluminar a los que viven en tiniebla_y en sombra de muerte,_para guiar nuestros pasos_por el camino de la paz.");
        String ss = "Bendito sea el Señor, Dios de Israel,_porque ha visitado y redimido a su pueblo,_suscitándonos una fuerza de salvación_en la casa de David, su siervo,_según lo había predicho desde antiguo_por boca de sus santos profetas.§Es la salvación que nos libra de nuestros enemigos_y de la mano de todos los que nos odian;_ha realizado así la misericordia que tuvo con nuestros padres,_recordando su santa alianza_y el juramento que juró a nuestro padre Abraham.§Para concedernos que, libres de temor,_arrancados de la mano de los enemigos,_le sirvamos con santidad y justicia,_en su presencia, todos nuestros días.§Y a ti, niño, te llamarán profeta del Altísimo,_porque irás delante del Señor_a preparar sus caminos,_anunciando a su pueblo la salvación,_el perdón de sus pecados.§Por la entrañable misericordia de nuestro Dios,_nos visitará el sol que nace de lo alto,_para iluminar a los que viven en tiniebla_y en sombra de muerte,_para guiar nuestros pasos_por el camino de la paz.";

        //String s=Utils.getFormato(ss);
        Spanned sp = Utils.fromHtml(ss);
        return Utils.fromHtml(ss);//Utils.getFormato(ss);


    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public SpannableStringBuilder getHeader() {

        return Utils.formatTitle("CÁNTICO EVANGÉLICO");
    }
}