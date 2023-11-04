package javaapplication4;

public class HtmlDoc {
    /*
     * Torna um documento HTML possível de ser lido em um interpretador HTML 3.2.
     * Não retira estilos e scripts.
     * 
     * @param html Documento HTML
     * 
     * @return Documento HTML convertido
     */
    public static String toHtml3_2(String html) {
        String convertedHtml = html;

        // Retira indentificador do documento
        convertedHtml = convertedHtml.replaceAll("<!(?i)DOCTYPE html>", "");

        // Retira tags inválidas
        convertedHtml = convertedHtml.replaceAll(
                "(?i)<(/?(del|ins|kbd|q|samp|var|dfn|object|embed|param|iframe|map|area|noscript|applet|basefont|bgsound|command|datalist|details|embed|keygen|mark|menu|meter|output|progress|rp|rt|ruby|source|time|track|wbr|article|aside|canvas|figure|figcaption|footer|header|hgroup|nav|section|summary|audio|video|svg|math|meta)[^>]*)>",
                "");

        return convertedHtml;
    }
}
