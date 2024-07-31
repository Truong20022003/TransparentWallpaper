package com.example.wallpaper.ViewModel;

import com.example.wallpaper.R;
import com.example.wallpaper.Model.ModelLanguage;
import com.example.wallpaper.ViewModel.BaseViewModel.BaseViewModel;
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class LanguageViewModel extends BaseViewModel {
    private final MutableLiveData<List<ModelLanguage>> languages = new MutableLiveData<>();
    private final MutableLiveData<ModelLanguage> selectedLanguage = new MutableLiveData<>();
    private final MutableLiveData<String> _langDevice = new MutableLiveData<>();
    private final MutableLiveData<String> _codeLang = new MutableLiveData<>();

    public LiveData<List<ModelLanguage>> getLanguages() {
        return languages;
    }

    public LiveData<ModelLanguage> getSelectedLanguage() {
        return selectedLanguage;
    }

    public LiveData<String> getLangDevice() {
        return _langDevice;
    }

    public LiveData<String> getCodeLang() {
        return _codeLang;
    }

    public void setLocale(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        android.content.res.Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public void language(Context context) {
        String langDevice = "en";
        String codeLang = "en";
        int position = 0;
        boolean isLangDefault = false;

        List<ModelLanguage> list = new ArrayList<>();
        list.add(new ModelLanguage("en", R.drawable.img_frag_eng, "English", false));
        list.add(new ModelLanguage("hi", R.drawable.img_frag_hindi, "Hindi", false));
        list.add(new ModelLanguage("es", R.drawable.img_frag_spanish, "Spanish", false));
        list.add(new ModelLanguage("fr", R.drawable.img_frag_french, "French", false));
        list.add(new ModelLanguage("de", R.drawable.img_frag_german, "German", false));
        list.add(new ModelLanguage("in", R.drawable.img_frag_indo, "Indonesian", false));
        list.add(new ModelLanguage("pt", R.drawable.img_frag_portu, "Portuguese", false));
        list.add(new ModelLanguage("zh", R.drawable.img_frag_china, "China", false));

        _langDevice.postValue(langDevice);
        _codeLang.postValue(codeLang);
        languages.postValue(list);
    }

    public void setSelectedLanguage(Context context, ModelLanguage language) {
        selectedLanguage.setValue(language);
        _codeLang.postValue(language.getIdLanguage());
        saveSelectedLanguage(context, language.getIdLanguage());
    }

    private void saveSelectedLanguage(Context context, String languageCode) {
        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("selected_language", languageCode).apply();
    }
}
