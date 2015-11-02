/**
 * Created by mickey on 15/10/27.
 */

window.onload = function(){
    cc.game.onStart = function(){
        //load resources
        cc.LoaderScene.preload(["img/HelloWorld.png"], function () {
            var MyScene = cc.Scene.extend({
                onEnter:function () {
                    this._super();
                    //this.setOpacity(255);
                    var layer = new cc.LayerColor(cc.color(0, 0, 0, 0));
                    this.addChild(layer);
                    var size = cc.director.getWinSize();
                    var sprite = cc.Sprite.create("img/HelloWorld.png");
                    sprite.setPosition(size.width / 2, size.height / 2);
                    sprite.setScale(0.2);
                    layer.addChild(sprite, 0);
					
                    var label = cc.LabelTTF.create("Hello World", "Arial", 40);
                    label.setPosition(size.width / 2, size.height / 2);
                    layer.addChild(label, 1);
                }
            });
            cc.director.runScene(new MyScene());
        }, this);
    };
    cc.game.run("gameCanvas");
};