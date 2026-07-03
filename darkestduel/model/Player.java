package darkestduel.model;

import darkestduel.classes.*;
import darkestduel.effects.*;

import java.util.List;


public class Player {
        private String name;
        private String symbol;
        private CharacterClass characterClass;
        private int position;

        // Encapsulamento: HP e AC são privados e alterados apenas por métodos.
        private int hp;
        private int ac;

        private List<StatusEffect> statusEffects = new ArrayList<>();
        private Map<String, Integer> cooldowns = new LinkedHashMap<>();

        public Player(String name, String symbol, CharacterClass characterClass, int position) {
            this.name = name;
            this.symbol = symbol;
            this.characterClass = characterClass;
            this.position = position;
            this.hp = characterClass.getMaxHp();
            this.ac = 0;
        }

        public String getName() {
            return name;
        }

        public String getSymbol() {
            return symbol;
        }

        public CharacterClass getCharacterClass() {
            return characterClass;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getHp() {
            return hp;
        }

        public int getAc() {
            return ac;
        }

        public int getMaxHp() {
            return characterClass.getMaxHp();
        }

        public Map<String, Integer> getCooldowns() {
            return cooldowns;
        }

        public boolean isAlive() {
            return hp > 0;
        }

        public void addAc(int amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Não é possível adicionar uma quantidade negativa de AC.");
            }
            ac += amount;
        }

        public void gainTurnAc() {
            addAc(characterClass.getAcPerTurn());
        }

        public void spendAc(int amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Não é possível gastar uma quantidade negativa de AC.");
            }

            if (amount > ac) {
                throw new IllegalArgumentException(name + " não possui AC suficiente.");
            }

            ac -= amount;
            reduceCooldownsBy(amount);
        }

        private void reduceCooldownsBy(int consumedAc) {
            List<String> keys = new ArrayList<>(cooldowns.keySet());
            for (String key : keys) {
                int updated = Math.max(0, cooldowns.get(key) - consumedAc);
                if (updated == 0) {
                    cooldowns.remove(key);
                } else {
                    cooldowns.put(key, updated);
                }
            }
        }

        public void setCooldown(String actionKey, int cooldown) {
            if (cooldown > 0) {
                cooldowns.put(actionKey, cooldown);
            }
        }

        public int cooldownRemaining(String actionKey) {
            return cooldowns.getOrDefault(actionKey, 0);
        }

        public int heal(int amount) {
            int oldHp = hp;
            hp = Math.min(getMaxHp(), hp + amount);
            return hp - oldHp;
        }

        public DamageReport receiveDamage(int rawDamage, Player attacker) {
            List<String> messages = new ArrayList<>();
            int damage = Math.max(0, rawDamage);

            for (StatusEffect effect : new ArrayList<>(statusEffects)) {
                DamageModification modification = effect.modifyIncomingDamage(this, attacker, damage);
                damage = modification.getDamage();
                messages.addAll(modification.getMessages());
            }

            damage = Math.max(0, damage);
            hp = Math.max(0, hp - damage);

            for (StatusEffect effect : new ArrayList<>(statusEffects)) {
                messages.addAll(effect.onOwnerAttacked(this, attacker, damage));
            }

            removeExpiredEffects();
            return new DamageReport(damage, messages);
        }

        public void addStatusEffect(StatusEffect effect) {
            statusEffects.removeIf(activeEffect -> activeEffect.getName().equals(effect.getName()));
            statusEffects.add(effect);
        }

        public List<String> triggerTurnStartEffects(Player opponent, Arena arena) {
            List<String> messages = new ArrayList<>();
            for (StatusEffect effect : new ArrayList<>(statusEffects)) {
                messages.addAll(effect.onTurnStart(this, opponent, arena));
            }
            removeExpiredEffects();
            return messages;
        }

        public List<String> triggerTurnEndEffects() {
            List<String> messages = new ArrayList<>();
            for (StatusEffect effect : new ArrayList<>(statusEffects)) {
                messages.addAll(effect.onTurnEnd(this));
            }
            removeExpiredEffects();
            return messages;
        }

        private void removeExpiredEffects() {
            statusEffects.removeIf(StatusEffect::isExpired);
        }

        public String statusSummary() {
            if (statusEffects.isEmpty()) {
                return "Nenhum";
            }

            StringBuilder summary = new StringBuilder();
            for (int i = 0; i < statusEffects.size(); i++) {
                StatusEffect effect = statusEffects.get(i);
                if (i > 0) {
                    summary.append(", ");
                }
                summary.append(effect.getName()).append("(").append(effect.getDuration()).append(")");
            }
            return summary.toString();
        }
    }