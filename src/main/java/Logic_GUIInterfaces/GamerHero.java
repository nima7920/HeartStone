package Logic_GUIInterfaces;

import GameCards.Weapon;
import GameHeros.Hero;
import GameHeros.HeroClass;
import GameHeros.Mage;

public class GamerHero extends GameChar {

    private Hero hero;
    private GamerWeapon gamerWeapon;

    public GamerHero(Hero hero) {
        this.hero = hero;
        this.hp = hero.getHp();
        this.name=hero.toString();
        this.gamerWeapon = null;
    }

    public GamerWeapon getGamerWeapon() {
        return gamerWeapon;
    }

    public void setGamerWeapon(Weapon weapon) {
        this.gamerWeapon=new GamerWeapon(weapon);
    }

    public Hero getHero(){
        return hero;
    }
}
