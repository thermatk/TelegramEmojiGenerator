package com.thermatk.java.tgemoji;

import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;


/*
public class Hook implements IXposedHookZygoteInit, IXposedHookLoadPackage {
    static String MODULE_PATH;
    static final String[] replace = {
            "auction_highestBid_purple.png",
            "auctionMarker_purple.png",
            "bubble_purple.png",
            "checkbox_purple.png",
            "checkbox_purple_checked.png",
            "drag_purple.png",
            "propertymarker03.pvr",
            "tabletop_purple.png",
            "tabletop_purpleLg.png",
            "tokenhili.pvr"
    };

    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) throws Throwable {
        XSharedPreferences prefs = new XSharedPreferences(Main.class.getPackage().getName(), Main.PREFS);
        MODULE_PATH = startupParam.modulePath;
        prefs.makeWorldReadable();
    }

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        XSharedPreferences prefs = new XSharedPreferences(Main.class.getPackage().getName(), Main.PREFS);

        if (prefs.getBoolean(Main.PREF_FIX_MONOPOLY, false) &&
                lpparam.packageName.startsWith("com.eamobile.monopoly_full"))
            monoPatch(lpparam);
    }

    public void monoPatch(LoadPackageParam lpparam) {
        findAndHookMethod("android.content.res.AssetManager", lpparam.classLoader,
                "open", String.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        for (String s : replace) {
                            if (((String)param.args[0]).endsWith("/"+s)) {
                                String a = new String(s);
                                if (a.endsWith(".pvr"))
                                    a += ".mp3";
                                XposedBridge.log("loading "+a);
                                param.setResult(XModuleResources.createInstance(MODULE_PATH, null).getAssets().open(a));
                                return;
                            }
                        }
                    }
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    }
                });
        findAndHookMethod("android.content.res.AssetManager", lpparam.classLoader,
                "openFd", String.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        for (String s : replace) {
                            if (((String)param.args[0]).endsWith("/"+s)) {
                                String a = new String(s);
                                if (a.endsWith(".pvr"))
                                    a += ".mp3";
                                XposedBridge.log("loading Fd "+a);
                                param.setResult(XModuleResources.createInstance(MODULE_PATH, null).getAssets().openFd(a));
                                return;
                            }
                        }
                    }
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    }
                });
    }
}*/
/**
 * Created by thermatk on 22.10.16.
 */
public class Tmpcode {

    public static String e1_emojiToFilename(String emoji) {

        int codepoints = emoji.toCharArray().length;
        String[] codes = new String[codepoints];
        for (int i=0; i<codepoints;i++) {
            codes[i] = Integer.toHexString(Character.codePointAt(emoji,i));
        }
        String filename;
        filename = "";
        switch (codepoints) {
            case 1:
                filename = codes[0];
                break;
            case 2:
                filename = codes[0];
                break;
            case 4:
                filename = codes[0]+"-"+codes[2];
                break;
            case 5:
                filename = codes[0]+"-"+codes[2]+"-"+codes[4];
                break;
            case 6:
                filename = codes[0]+"-"+codes[2]+"-"+codes[5];
                break;
            case 8:
                filename = codes[0]+"-"+codes[3]+"-"+codes[6];
                break;
            case 11:
                filename = codes[0]+"-"+codes[3]+"-"+codes[6]+"-"+codes[9];
                break;
            default:
                System.out.println("ERROR, check codepoints #" + codepoints + "for emoji " + emoji);
                break;
        }
        return filename;
    }

    // from https://gist.github.com/heyarny/71c246f2f7fa4d9d10904fb9d5b1fa1d
    private static String toCodePoint(String unicodeSurrogates, String sep) {
        ArrayList<String> r = new ArrayList<String>();
        int c = 0, p = 0, i = 0;
        if (sep == null)
            sep = "-";
        while (i < unicodeSurrogates.length()) {
            c = unicodeSurrogates.charAt(i++);
            if (p != 0) {
                r.add(Integer.toString((0x10000 + ((p - 0xD800) << 10) + (c - 0xDC00)), 16));
                p = 0;
            } else if (0xD800 <= c && c <= 0xDBFF) {
                p = c;
            } else {
                r.add(Integer.toString(c, 16));
            }
        }
        return StringUtils.join(r, sep);
    }
    private static String grabTheRightIcon(String rawText) {
        // if variant is present as \uFE0F
        String result;
        result = toCodePoint(
                rawText.indexOf('\u200D') < 0 ?
                        rawText.replace("\uFE0F", "") :
                        rawText, null);
        //fix fe0f
        if (rawText.indexOf('\uFE0F') > 0) {
            result += "-fe0f";
        }
        return result;
    }
}
