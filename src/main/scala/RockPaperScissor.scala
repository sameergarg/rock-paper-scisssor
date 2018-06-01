import RockPaperScissor.{Action, Result}
import RockPaperScissor.Action.{paper, pick, rock, scissor}
import RockPaperScissor.Result.{Lose, Win}

import scala.util.Random

trait RockPaperScissor {
  def play(player1: Action, player2: Action): Result = {
    println(s"You: $player1, Computer: $player2")
    (player1, player2) match {
      case (`rock`, `paper`)      => Lose
      case (`rock`, `scissor`)    => Win
      case (`paper`, `scissor`)   => Lose
      case (`paper`, `rock`)      => Win
      case (`scissor`, `rock`)    => Lose
      case (`scissor`, `paper`)   => Win
      case _                      =>
        println("Play again\n")
        play(askPlayer, pick)

    }
  }

  def askPlayer: Action = {
    val playerAction = scala.io.StdIn.readLine("Pick from : rock, paper or scissor\n")
    Action.fromString(playerAction).getOrElse(askPlayer)
  }
}

object RockPaperScissor extends App with RockPaperScissor {

  sealed trait Action

  object Action {
    case object rock extends Action
    case object paper extends Action
    case object scissor extends Action

    val actions = List(rock, paper, scissor)

    def pick = actions(Random.nextInt(3))

    def fromString(str: String): Option[Action] = actions.find(_.toString == str.toLowerCase)
  }

  sealed trait Result
  object Result {
    case object Win extends Result
    case object Lose extends Result
  }


  play(askPlayer, pick) match {
    case `Win` => println("You win")
    case `Lose` => println("You lost")
  }







}
