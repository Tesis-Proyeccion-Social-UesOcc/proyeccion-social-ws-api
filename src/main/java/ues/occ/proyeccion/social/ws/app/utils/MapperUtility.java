package ues.occ.proyeccion.social.ws.app.utils;

public class MapperUtility {
    public static boolean isComplete(String isCompleteString) {
        return map(isCompleteString);
    }

    public static boolean isAprobado(String isCompleteString) {
        return map(isCompleteString);
    }

    private static boolean map(String charSequence){
        if ("si".equals(charSequence)) {
            return true;
        } else if ("no".equals(charSequence)) {
            return false;
        } else {
            throw new IllegalArgumentException("Invalid parameters, valid are: si or no");
        }
    }
}
