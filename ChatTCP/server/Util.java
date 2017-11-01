package server;

/**
 *
 * @author Davide Porcu
 */
public class Util {

    public static final String PATTERN_NOME = "^[A-Z]{1}[a-zA-Z' ]+";
    public static final String PATTERN_USERNAME = "^[A-Za-z0-9_.]+$";
    public static final String PATTERN_PASSWORD = "^.*(?=.{4,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$";
    public static final String PATTERN_TELEFONO = "^[0-9]{10}";

    public static String validateAccount(String username,String password,String cellulare) {
        String errReport = "";
        errReport += isUsernameValido(username);
        errReport += isPasswordValide(password, password);
        errReport += isTelefonoValido(cellulare);
        return errReport;
    }

    public static String validate(String nome, String cognome, String username, String password1, String password2, String tipoTelefono, String telefono, String città, String via) {
        String errReport = "";
        errReport += isNomeValido(nome);
        errReport += isCognomeValido(cognome);
        errReport += isUsernameValido(username);
        errReport += isPasswordValide(password1, password2);
        errReport += isTelefonoValido(telefono);
        return errReport;
    }

    public static String validatePersona(String nome, String cognome, String città, String via) {
        String errReport = "";
        errReport += isNomeValido(nome);
        errReport += isCognomeValido(cognome);
        return errReport;
    }

    public static String validateTelefono(String tipoTelefono,String telefono){
        String errReport = "";
        errReport += isTelefonoValido(telefono);
        return errReport;
    }
    

    public static String validateModPassword(String passAccount, String oldPw, String newPw, String newPw2) {
        String errReport = "";
        if (oldPw == null || oldPw.isEmpty()) {
            errReport += "Compila tutti i campi ";
        } else if (!passAccount.equals(oldPw)) {
            errReport += "le password non corrispondono ";
        } else {
            errReport += isPasswordValide(newPw, newPw2);
        }
        return errReport;
    }

    public static String isNomeValido(String nome) {
        String errReport = "";
        if (nome == null || nome.isEmpty()) {
            errReport += "nome obbligatorio ";
        } else if (!nome.matches(PATTERN_NOME)) {
            errReport += "nome non valido ";
        }
        return errReport;
    }

    public static String isCognomeValido(String cognome) {
        String errReport = "";
        if (cognome == null || cognome.isEmpty()) {
            errReport += "cognome obbligatorio ";
        } else if (!cognome.matches(PATTERN_NOME)) {
            errReport += "cognome non valido ";
        }
        return errReport;
    }

    public static String isUsernameValido(String username) {
        String errReport = "";
        if (username == null || username.isEmpty()) {
            errReport += "username obbligatorio ";
        } else if (!username.matches(PATTERN_USERNAME)) {
            errReport += "username non valido ";
        } else if (SQLHelper.esisteUsername(username)) {//controllare che non esista già
            errReport += "username non disponibile ";
        }
        return errReport;
    }

    public static String isPasswordValide(String password1, String password2) {
        String errReport = "";
        if (password1 == null || password1.isEmpty() || password2 == null || password2.isEmpty()) {
            errReport += "password obbligatoria ";
        } else if (!password1.matches(PATTERN_PASSWORD)) {
            errReport += "La password deve contenere almeno una cifra, una lettera maiuscola e minuscola e deve essere lunga almeno 8 caratteri ";
        } else if (!password2.equals(password1)) {
            errReport += "Le due password non coincidono ";
        }
        return errReport;
    }

    public static String isTelefonoValido(String telefono) {
        String errReport = "";
        if (telefono == null || telefono.isEmpty()) {
            errReport += "telefono obbligatorio ";
        } else if (!telefono.matches(PATTERN_TELEFONO)) {
            errReport += "telefono non valido ";
        }
        return errReport;
    }
}
