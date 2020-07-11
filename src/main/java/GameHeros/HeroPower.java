package GameHeros;

public class HeroPower {
  private HeroClass heroClass;
  // 0 for passive , 1 for no target and 2 for enemy target
  private int targetType=0;
  private int manaCost;

    public HeroPower(HeroClass heroClass, int targetType, int manaCost) {
        this.heroClass = heroClass;
        this.targetType = targetType;
        this.manaCost = manaCost;
    }

    public HeroClass getHeroClass() {
        return heroClass;
    }

    public void setHeroClass(HeroClass heroClass) {
        this.heroClass = heroClass;
    }

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
}
