classDiagram

class TheDarkestDuel {
  +main(String[] args)
  -createManualGame() Game
  -chooseCharacterClass(String) CharacterClass
}

class Game {
  -Arena arena
  -Player playerA
  -Player playerB
  -HumanController controllerA
  -HumanController controllerB
  -int turnCounter
  +run()
  -playTurn(Player, Player, HumanController)
  -showResult()
}

class TurnData {
  -Player actor
  -Player opponent
  -HumanController controller
}

class HumanController {
  +chooseAction(Player, Player, Arena) Action
  -actionStatus(Action, Player, Player, Arena) String
}

class Arena {
  -int size
  -Map~Integer, FireHazard~ fireHazards
  +distanceBetween(Player, Player) int
  +previewMovement(Player, Player, int) MovementPreview
  +movePlayer(Player, Player, int) List~String~
  +addFireZone(int, int, int, int) List~String~
  +triggerTileEffects(Player) List~String~
  +render(Player, Player) String
}

class FireHazard {
  -int duration
  -int damage
  +tick()
  +isExpired() boolean
}

class Player {
  -String name
  -String symbol
  -CharacterClass characterClass
  -int position
  -int hp
  -int ac
  -List~StatusEffect~ statusEffects
  -Map~String, Integer~ cooldowns
  +gainTurnAc()
  +spendAc(int)
  +receiveDamage(int, Player) DamageReport
  +addStatusEffect(StatusEffect)
  +triggerTurnStartEffects(Player, Arena) List~String~
  +triggerTurnEndEffects() List~String~
  +isAlive() boolean
}

class CharacterClass {
  <<abstract>>
  #String name
  #int maxHp
  #int acPerTurn
  #int diceAmount
  #int diceSides
  #int maxMovement
  #int separationPenalty
  #double criticalChance
  #int criticalMultiplier
  +rollDamage(int) RollResult
  +movementCost(int) int
  +getMovementActions() List~Action~
  +getActions()* List~Action~
}

class Warrior
class Archer
class Assassin
class SpearMaster

class Action {
  <<abstract>>
  #String name
  #String description
  #int acCost
  #int cooldown
  #boolean endsTurn
  +canExecute(Player, Player, Arena) boolean
  +unavailableReason(Player) String
  #payAndApplyCooldown(Player)
  +execute(Player, Player, Arena)* List~String~
}

class WaitAction
class MovementAction {
  -int steps
}
class AttackAction {
  -int minRange
  -int maxRange
  -int damageMultiplier
}
class DefenseAction
class EvasionAction {
  -double chance
}
class CounterAttackAction {
  -int acBonus
}
class HealAction {
  -int duration
  -int healPerTurn
}
class FireBombAction {
  -int minRange
  -int maxRange
  -int radius
  -int duration
  -int fireDamage
}

class StatusEffect {
  <<abstract>>
  #String name
  #int duration
  +onTurnStart(Player, Player, Arena) List~String~
  +modifyIncomingDamage(Player, Player, int) DamageModification
  +onOwnerAttacked(Player, Player, int) List~String~
  +onTurnEnd(Player) List~String~
  +isExpired() boolean
}

class DefenseEffect {
  -double reduction
}
class EvasionEffect {
  -double chance
}
class CounterAttackEffect {
  -int acBonus
}
class RegenerationEffect {
  -int healPerTurn
}

class RollResult {
  -List~Integer~ rolls
  -int baseDamage
  -boolean critical
  -int totalDamage
  +describe() String
}

class DamageReport {
  -int damage
  -List~String~ messages
}

class DamageModification {
  -int damage
  -List~String~ messages
}

class MovementPreview {
  -boolean valid
  -int newPosition
  -String message
}

TheDarkestDuel ..> Game : cria
TheDarkestDuel ..> CharacterClass : seleciona

Game *-- Arena
Game *-- Player
Game *-- HumanController
Game ..> TurnData

TurnData --> Player : actor/opponent
TurnData --> HumanController

HumanController ..> Action : escolhe
HumanController ..> Arena
HumanController ..> Player

Player *-- CharacterClass
Player o-- StatusEffect
Player ..> DamageReport
Player ..> DamageModification

Arena *-- FireHazard
Arena ..> Player
Arena ..> MovementPreview
Arena ..> DamageReport

CharacterClass <|-- Warrior
CharacterClass <|-- Archer
CharacterClass <|-- Assassin
CharacterClass <|-- SpearMaster
CharacterClass ..> Action : cria ações
CharacterClass ..> RollResult : rola dano

Action <|-- WaitAction
Action <|-- MovementAction
Action <|-- AttackAction
Action <|-- DefenseAction
Action <|-- EvasionAction
Action <|-- CounterAttackAction
Action <|-- HealAction
Action <|-- FireBombAction

Action ..> Player
Action ..> Arena

AttackAction ..> RollResult
AttackAction ..> DamageReport
MovementAction ..> MovementPreview
DefenseAction ..> DefenseEffect
EvasionAction ..> EvasionEffect
CounterAttackAction ..> CounterAttackEffect
HealAction ..> RegenerationEffect
FireBombAction ..> Arena : cria zona de fogo

StatusEffect <|-- DefenseEffect
StatusEffect <|-- EvasionEffect
StatusEffect <|-- CounterAttackEffect
StatusEffect <|-- RegenerationEffect

StatusEffect ..> DamageModification
StatusEffect ..> Player
StatusEffect ..> Arena