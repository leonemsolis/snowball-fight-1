package com.doublew.snowballfight.game.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.doublew.snowballfight.R;


public class ResourceManager {
    public static Bitmap mapBitmap, heroIconBitmap,
            background1, background2, background3,
            background4, snowball, snowball_shadow, power_bar,
            school, game_ui, shop0, shop1,
            bottom_panel, top_panel, effect0, effect1,
            item_frame_selected, backpack, prompt, notification,
            all_clear, sp1, sp2, sp3, aim_panel, fire_panel, cant_shoot_panel;
    public static Bitmap boss1Sprites[];
    public static Bitmap boss2Sprites[];
    public static Bitmap boss3Sprites[];
    public static Bitmap boss4Sprites[];
    public static Bitmap enemy1Sprites[];
    public static Bitmap enemy2Sprites[];
    public static Bitmap heroSprites[];
    public static Bitmap currentBackground;
    public static Bitmap animation1[];
    public static Bitmap animation2[];
    public static Bitmap animation3[];
    public static Bitmap items[];
    public static Bitmap items_b[];
    public static Bitmap special[];
    private static SoundPool sound;
    private static int explosion, fire, fire2, greetings, hit, hurt, lose, music, victory;
    private static int streamID = 0;

    public static boolean sound_settings;
    private static Context context;
    public static boolean loaded;

    public ResourceManager(Context context) {
        this.context = context;
        loaded = false;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        mapBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.village, options);
        heroIconBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero_icon, options);
        school = BitmapFactory.decodeResource(context.getResources(), R.drawable.school, options);

        background1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.back1, options);
        background2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.back2, options);
        background3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.back3, options);
        background4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.back4, options);

        snowball = BitmapFactory.decodeResource(context.getResources(), R.drawable.snowball, options);
        snowball_shadow = BitmapFactory.decodeResource(context.getResources(), R.drawable.snowball_shadow, options);
        power_bar = BitmapFactory.decodeResource(context.getResources(), R.drawable.power, options);
        game_ui = BitmapFactory.decodeResource(context.getResources(), R.drawable.ui, options);

        bottom_panel = BitmapFactory.decodeResource(context.getResources(), R.drawable.bottom_panel, options);
        top_panel = BitmapFactory.decodeResource(context.getResources(), R.drawable.top_panel, options);
        shop0 = BitmapFactory.decodeResource(context.getResources(), R.drawable.shop1, options);
        shop1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.shop0, options);
        effect0 = BitmapFactory.decodeResource(context.getResources(), R.drawable.hyo0, options);
        effect1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.hyo1, options);
        item_frame_selected = BitmapFactory.decodeResource(context.getResources(), R.drawable.item_frame_selected, options);
        backpack = BitmapFactory.decodeResource(context.getResources(), R.drawable.backpack, options);
        prompt = BitmapFactory.decodeResource(context.getResources(), R.drawable.prompt, options);
        notification = BitmapFactory.decodeResource(context.getResources(), R.drawable.notification, options);
        all_clear = BitmapFactory.decodeResource(context.getResources(), R.drawable.all_clear, options);
        aim_panel = BitmapFactory.decodeResource(context.getResources(), R.drawable.aiming_panel, options);
        fire_panel = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_panel, options);
        cant_shoot_panel = BitmapFactory.decodeResource(context.getResources(), R.drawable.cant_shoot_panel, options);

        boss1Sprites = new Bitmap[4];
        boss2Sprites = new Bitmap[4];
        boss3Sprites = new Bitmap[4];
        boss4Sprites = new Bitmap[4];

        enemy1Sprites = new Bitmap[4];
        enemy2Sprites = new Bitmap[4];

        boss1Sprites[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss10, options);
        boss1Sprites[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss11, options);
        boss1Sprites[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss12, options);
        boss1Sprites[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss13, options);

        boss2Sprites[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss20, options);
        boss2Sprites[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss21, options);
        boss2Sprites[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss22, options);
        boss2Sprites[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss23, options);

        boss3Sprites[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss30, options);
        boss3Sprites[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss31, options);
        boss3Sprites[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss32, options);
        boss3Sprites[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss33, options);

        boss4Sprites[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss40, options);
        boss4Sprites[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss41, options);
        boss4Sprites[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss42, options);
        boss4Sprites[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss43, options);

        enemy1Sprites[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy00, options);
        enemy1Sprites[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy01, options);
        enemy1Sprites[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy02, options);
        enemy1Sprites[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy03, options);

        enemy2Sprites[0]  = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy10, options);
        enemy2Sprites[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy11, options);
        enemy2Sprites[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy12, options);
        enemy2Sprites[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy13, options);

        heroSprites = new Bitmap[7];

        heroSprites[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero0, options);
        heroSprites[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero1, options);
        heroSprites[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero2, options);
        heroSprites[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero3, options);
        heroSprites[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero4, options);
        heroSprites[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero_lose, options);
        heroSprites[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero_vic, options);

        animation1 = new Bitmap[2];

        animation1[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bbang0, options);
        animation1[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bbang1, options);

        animation2 = new Bitmap[1];

        animation2[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.h_bbang, options);

        animation3 = new Bitmap[1];
        animation3[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.v, options);

        items = new Bitmap[9];
        items[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item0, options);
        items[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item1, options);
        items[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item2, options);
        items[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item3, options);
        items[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item4, options);
        items[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item5, options);
        items[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item6, options);
        items[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item7, options);
        items[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item8, options);

        items_b = new Bitmap[9];
        items_b[0] = null;
        items_b[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item1_b, options);
        items_b[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item2_b, options);
        items_b[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item3_b, options);
        items_b[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item4_b, options);
        items_b[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item5_b, options);
        items_b[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item6_b, options);
        items_b[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item7_b, options);
        items_b[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item8_b, options);

        special = new Bitmap[3];
        special[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.special0, options);
        special[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.special1, options);
        special[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.special2, options);

        sp1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp1, options);
        sp2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp2, options);
        sp3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.sp3, options);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sound = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .build();
            sound.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId,
                                           int status) {
                    loaded = true;
                }
            });
        } else {
            sound = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
            sound.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId,
                                           int status) {
                    loaded = true;
                }
            });
        }
        explosion = sound.load(context, R.raw.explosion, 1);
        fire = sound.load(context, R.raw.fire, 1);
        fire2 = sound.load(context, R.raw.fire2, 1);
        greetings = sound.load(context, R.raw.greetings, 1);
        hit = sound.load(context, R.raw.hit, 1);
        hurt = sound.load(context, R.raw.hurt, 1);
        lose = sound.load(context, R.raw.lose, 1);
        music = sound.load(context, R.raw.music, 1);
        victory = sound.load(context, R.raw.victory, 1);
    }

    public static void playSound(int id) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.PREFERENCES_ID), Context.MODE_PRIVATE);
        if(sp.getBoolean(context.getString(R.string.SOUND_STATE), true)) {
            sound.stop(streamID);
            switch (id) {
                case 0:
                    streamID = sound.play(explosion, 1, 1, 1, 0, 1);
                    break;
                case 1:
                    streamID = sound.play(fire, 1, 1, 1, 0, 1);
                    break;
                case 2:
                    streamID = sound.play(fire2, 1, 1, 1, 0, 1);
                    break;
                case 3:
                    streamID = sound.play(greetings, 1, 1, 1, 0, 1);
                    break;
                case 4:
                    streamID = sound.play(hit, 1, 1, 1, 0, 1);
                    break;
                case 5:
                    streamID = sound.play(hurt, 1, 1, 1, 0, 1);
                    break;
                case 6:
                    streamID = sound.play(lose, 1, 1, 1, 0, 1);
                    break;
                case 7:
                    streamID = sound.play(music, 1, 1, 1, 0, 1);
                    break;
                case 8:
                    streamID = sound.play(victory, 1, 1, 1, 0, 1);
                    break;
            }
        }
    }

    public static void stopSound() {
        sound.stop(streamID);
    }
}
