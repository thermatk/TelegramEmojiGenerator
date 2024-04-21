package com.thermatk.java.tgemoji;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.thermatk.java.tgemoji.EmojiData.fixEmoji;

class DrawableInfo {
    public final byte page;
    public final short page2;
    public final int emojiIndex;

    public DrawableInfo(byte p, short p2, int index) {
        page = p;
        page2 = p2;
        emojiIndex = index;
    }
}

class PicInfo {
    public HashMap<String, DrawableInfo> drInfMap;
    public int totalRows;
    public int totalCols;
}
public class Main {
    public static final String basePath = "workfiles/";
    public static HashMap<String, DrawableInfo> rects = new HashMap<>();

    public static HashMap<String, PicInfo> pics = new HashMap<>();
    public static HashMap<String, String> nameList = new HashMap<>();

    public static void main(String[] args) {
        readFixedNames();
        doTheMap();
        System.out.print("Make img Noto");
        //makeImgsTwit();
        makeImgsGoog2();
        System.out.print("Done");
    }

    public static void makeImgsTwit() {

        for (Map.Entry<String, DrawableInfo> entry: rects.entrySet()) {
            try {
                DrawableInfo drInfo = entry.getValue();
                String emojiKey = entry.getKey();

                String path = basePath + "inputs/twemoji-master14/72x72/" + emojiKey + ".png";
                File f = new File(path);
                boolean exists = false;
                if(f.exists()) {
                    exists = true;
                } else {
                    // try quick fe0f fixes
                    String feofPath = path;
                    feofPath = feofPath.replace("-fe0f.", ".");
                    f = new File(feofPath);
                    if(f.exists()) {
                        exists = true;
                    } else {
                        feofPath = path.replace("-fe0f-", "-");
                        feofPath = path.replace("-fe0f", "");
                        f = new File(feofPath);
                        if(f.exists()) {
                            exists = true;
                        } else {
                            path = path.replace("-200d-", "-fe0f-200d-");
                            f = new File(path);
                            if(f.exists()) {
                                exists = true;
                            } else {
                                path = path.replace("-fe0f.", ".");
                                f = new File(path);
                                if(f.exists()) {
                                    exists = true;
                                }
                            }
                        }
                    }
                }

                if (exists) {
                    BufferedImage image72 = ImageIO.read(f);
                    BufferedImage image66 = resize(image72, 66,66);
                    ImageIO.write(image66, "PNG", new File(basePath+"ready/imgsTwemoji95/"+drInfo.page + "_" + drInfo.page2+".png"));
                } else {
                    System.out.println("(TWE) ERROR MISSING: " +drInfo.page + "_" + drInfo.page2 + "::"+ emojiKey);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    public static void makeImgsGoog() {

        for (Map.Entry<String, PicInfo> entry: pics.entrySet()) {
            PicInfo pInfo = entry.getValue();
            try {

                int w = (pInfo.totalCols + 1) * 66;
                int h = (pInfo.totalRows + 1) * 66;
                BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

                Graphics g = combined.getGraphics();

                for (Map.Entry<String, DrawableInfo> drEntry: pInfo.drInfMap.entrySet()) {
                    String pathKey = "emoji_u" + drEntry.getKey().replace("-","_");
                    String path = basePath + "inputs/noto-emoji-mar23/72/" + pathKey + ".png";
                    File f = new File(path);
                    boolean exists = false;
                    boolean fromFont = false;
                    if(f.exists()) {
                        exists = true;
                    } else {
                        // try a quick fe0f fix
                        path = path.replace("_fe0f_", "_");
                        path = path.replace("_fe0f", "");
                        f = new File(path);
                        if(f.exists()) {
                            exists = true;
                        } else {
                            // they append zeroes, fix
                            String[] cps = drEntry.getKey().split("-");
                            String newS = "";
                            for (int k=0;k<cps.length;k++) {
                                String cp = cps[k];
                                if (k>0) {
                                    newS +="_";
                                }

                                if (cp.length() == 2) {
                                    newS +="00" + cp;
                                } else {
                                    newS += cp;
                                }
                            }
                            pathKey = "emoji_u" + newS;
                            path = basePath + "inputs/noto-emoji-master/png/128/" + pathKey + ".png";
                            f = new File(path);
                            if(f.exists()) {
                                exists = true;
                            } else {
                                // use those extracted from Font
                                path = basePath + "inputs/notoEmojiExtracted/" + newS + ".png";
                                f = new File(path);
                                if(f.exists()) {
                                    exists = true;
                                    fromFont = true;
                                } else {
                                    // Twemoji fallback
                                    path = basePath + "inputs/twemoji-2.2.1/2/72x72/" + drEntry.getKey() + ".png";
                                    f = new File(path);
                                    if(f.exists()) {
                                        exists = true;
                                    }
                                }
                            }
                        }
                    }

                    if (exists) {
                        BufferedImage image128;
                        if (fromFont) {
                            image128 = ImageIO.read(f);
                            image128 = image128.getSubimage(4, 0, 128, 128);
                        } else {
                            image128 = ImageIO.read(f);
                        }

                        BufferedImage image64 = resize(image128, 64,64);
                        g.drawImage(image64,drEntry.getValue().rect.left, drEntry.getValue().rect.top, null);
                    } else {
                        System.out.println("(GOOGLE) ERROR MISSING: " + drEntry.getKey());
                    }
                }

                ImageIO.write(combined, "PNG", new File(basePath+"ready/imgsGoogleNoto/v14_emoji2.0x_"+entry.getKey()+".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    public static void makeImgsGoog2() {

        for (Map.Entry<String, DrawableInfo> entry: rects.entrySet()) {
            try {
                DrawableInfo drInfo = entry.getValue();
                String emojiKey = entry.getKey();
                String pathKey = "emoji_u" + emojiKey.replace("-","_");

                String path = basePath + "inputs/noto-emoji-mar23/72/" + pathKey + ".png";
                File f = new File(path);
                boolean exists = false;
                if(f.exists()) {
                    exists = true;
                } else {
                    // try quick fe0f fixes
                    String feofPath = path;
                    feofPath = feofPath.replace("_fe0f_", "_");
                    feofPath = feofPath.replace("_fe0f", "");
                    f = new File(feofPath);
                    if(f.exists()) {
                        exists = true;
                    } else {
                        // they append zeroes, fix
                        String[] cps = entry.getKey().split("-");
                        String newS = "";
                        for (int k=0;k<cps.length;k++) {
                            String cp = cps[k];
                            if (k>0) {
                                newS +="_";
                            }

                            if (cp.length() == 2) {
                                newS +="00" + cp;
                            } else {
                                newS += cp;
                            }
                        }
                        pathKey = "emoji_u" + newS;
                        path = basePath + "inputs/noto-emoji-mar23/72/" + pathKey + ".png";
                        f = new File(path);
                        if(f.exists()) {
                            exists = true;
                        } else {
                            // Twemoji fallback
                            path = basePath + "inputs/twemoji-master14/72x72/" + emojiKey + ".png";
                            f = new File(path);
                            if(f.exists()) {
                                exists = true;
                            }
                        }
                    }
                }

                if (exists) {
                    BufferedImage image72 = ImageIO.read(f);
                    BufferedImage image66 = resize(image72, 66,66);
                    ImageIO.write(image66, "PNG", new File(basePath+"ready/imgsNoto101/"+drInfo.page + "_" + drInfo.page2+".png"));
                } else {
                    System.out.println("(GOOGLE) ERROR MISSING: " +drInfo.page + "_" + drInfo.page2 + "::"+ emojiKey);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void readFixedNames() {

        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new FileReader(basePath+"convList.txt"));
            while ((line = br.readLine()) != null) {
                String[] emojis = line.split(",");
                for (String emoji: emojis) {
                    String[] oneEm = emoji.split(":");
                    nameList.put(oneEm[0],oneEm[1]);
                }
            }
        } catch (IOException e) {

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // code initally from https://github.com/DrKLO/Telegram/blob/master/TMessagesProj/src/main/java/org/telegram/messenger/Emoji.java
    public static void doTheMap() {
        for (int j = 0; j < EmojiData.data.length; j++) {
            int position;
            for (int i = 0; i < EmojiData.data[j].length; i++) {
                String name = nameList.get(fixEmoji(EmojiData.data[j][i]));
                if (name == null) {
                    // another fe0f fix
                    String fix = fixEmoji(EmojiData.data[j][i])+"\uFE0F";
                    name = nameList.get(fix);
                }
                rects.put(name, new DrawableInfo((byte) j, (short) i, i));
            }
        }

    }

    // from http://stackoverflow.com/a/9417836
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
