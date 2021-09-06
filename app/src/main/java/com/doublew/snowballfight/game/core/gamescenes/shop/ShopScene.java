package com.doublew.snowballfight.game.core.gamescenes.shop;

import android.content.Context;
import android.content.SharedPreferences;

import com.doublew.snowballfight.R;
import com.doublew.snowballfight.game.core.GameManager;
import com.doublew.snowballfight.game.core.ResourceManager;
import com.doublew.snowballfight.game.core.gamescenes.GameScene;
import com.doublew.snowballfight.game.cover.GameView;

import java.util.ArrayList;
import java.util.Iterator;

public class ShopScene extends GameScene {
    GameManager gameManager;
    float topY = (GameView.gameHeight- ResourceManager.shop0.getHeight())/2;
    float leftX = 0;
    float uiLeft, uiRight;
    float buyTop, buyBot;
    float exitTop, exitBot;
    float itemsTop, itemsBot;
    float firstLeft, firstRight, secondLeft, secondRight, thirdLeft, thirdRight, fourthLeft, fourthRight;
    private ArrayList<Integer> items;
    int promptID;
    int chosenItem;
    int gold;
    int priceList[];
    String notification;
        public ShopScene(GameManager gameManager, int stageID, Context context) {
        super(stageID, context);
        super.updater = new ShopUpdater(this);
        super.renderer = new ShopRenderer(this, stageID);
        this.gameManager = gameManager;

        chosenItem = -1;
        displayingContent = 0;
        notification = "";
        uiLeft = leftX + 19;
        uiRight = uiLeft + 104;
        buyTop = topY + 48;
        buyBot = buyTop + 29;
        exitTop = buyBot + 24;
        exitBot = exitTop + 27;

        itemsTop = exitBot + 77;
        itemsBot = itemsTop + 56;

        firstLeft = leftX + 35;
        firstRight = firstLeft + 56;

        secondLeft = firstRight + 8;
        secondRight = secondLeft + 56;

        thirdLeft = secondRight + 8;
        thirdRight = thirdLeft + 56;

        fourthLeft = thirdRight + 8;
        fourthRight = fourthLeft + 56;

        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.PREFERENCES_ID), Context.MODE_PRIVATE);
        gold = sp.getInt(context.getString(R.string.GOLD_PREFERENCES), 0);
        items = new ArrayList<>(5);
        for(int i = 0; i < 5; ++i) {
            items.add(sp.getInt(context.getString(R.string.ITEM_PREFERENCES)+i, -1));
        }

        priceList = new int[4];
        switch (stageID) {
            case 5: //item-shop
                priceList[0] = 5;
                priceList[1] = 8;
                priceList[2] = 8;
                priceList[3] = 14;
                break;
            case 6: //drug-shop
                priceList[0] = 6;
                priceList[1] = 12;
                priceList[2] = 10;
                priceList[3] = 12;
                break;
        }
    }

    private void createNotification(String text) {
        displayingContent = 1;
        notification = text;
    }

    void createPrompt(int promptID) {
        displayingContent = 2;
        this.promptID = promptID;
    }

    private void savePreferences() {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.PREFERENCES_ID), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for(int i = 0; i < 5; ++i) {
            editor.putInt(context.getString(R.string.ITEM_PREFERENCES)+i, items.get(i));
        }
        editor.putInt(context.getString(R.string.GOLD_PREFERENCES), gold);
        editor.apply();
    }

    void buyItem(int id) {
        if(gold < priceList[id]) {
            createNotification(context.getString(R.string.not_enough_gold));
            return;
        }
        boolean full = true;
        for(int i = 0; i < 5; ++i) {
            if(items.get(i) == -1) {
                full = false;
            }
        }
        if(full) {
            createNotification(context.getString(R.string.bag_is_full));
            return;
        }
        int prefix;
        if(stageID == 5) {
            prefix = 5;
        } else {
            prefix = 1;
        }
        for (int i: items) {
            if(i == prefix+id) {
                createNotification(context.getString(R.string.duplicate_item));
                return;
            }
        }

        Iterator<Integer>ii = items.iterator();
        while(ii.hasNext()) {
            if(ii.next() == -1) {
                ii.remove();
                break;
            }
        }

        items.add(id+prefix);
        gold -= priceList[id];
        createNotification(context.getString(R.string.bought_item));
        chosenItem = -1;
        savePreferences();
    }
}
