package client.legalease.Preference;


public class Session {
//    SharedPreferences prefs;
//    SharedPreferences.Editor editor;
//    Context ctx;
//
//
////    private static String PREF_KEY = "preference";
//
////    static SharedPreferences.Editor editor;
//
//    public Student getLoginSharedPref(Context context) {
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
//        String json = sharedPref.getString("LOGIN_PREF", null);
//        Gson gson = new Gson();
//        Type type = new TypeToken<Student>() {
//        }.getType();
//
//        Student beans = null;
//        try {
//            beans = gson.fromJson(json, type);
//        } catch (Exception e) {
//            return null;
//        }
//        return beans;
//    }
//
//    public void setLoginSharedPref(Context context, Student beans) {
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
//        editor = sharedPref.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(beans);
//        editor.putString("LOGIN_PREF", json);
//        editor.commit();
//
//    }
//
//
//
//
//
//
//
//
//    public Session(Context ctx){
//        this.ctx= ctx;
//        prefs = ctx.getSharedPreferences("myapp",Context.MODE_PRIVATE);
//        editor=prefs.edit();
//    }
//
//    public  void  setLoggedin(boolean loggedin){
//        editor.putBoolean("loggedInmode",loggedin);
//        editor.commit();
//    }
//    public boolean loggedin(){
//        return prefs.getBoolean("loggedInmode", false);
//    }
//
//    public void setToken(String token) {
//              editor.putString("Token", token);
//
//    }
}
