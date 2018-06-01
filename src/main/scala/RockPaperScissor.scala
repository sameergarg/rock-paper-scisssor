import RockPaperScissor.{Action, Result}
import RockPaperScissor.Action.{paper, pick, rock, scissor}
import RockPaperScissor.Result.{Lose, Win}
import cats.data.Writer
import cats.implicits._

import scala.util.Random

trait RockPaperScissor {
  def play(player1: Action, player2: Action): Writer[List[String], Result] = {
    val actions = s"You: $player1, Computer: $player2"
    (player1, player2) match {
      case (`rock`, `paper`)      => Writer(List(actions), Lose)
      case (`rock`, `scissor`)    => Writer(List(actions), Win)
      case (`paper`, `scissor`)   => Writer(List(actions), Lose)
      case (`paper`, `rock`)      => Writer(List(actions), Win)
      case (`scissor`, `rock`)    => Writer(List(actions), Lose)
      case (`scissor`, `paper`)   => Writer(List(actions), Win)
      case _                      => play(askPlayer, pick)
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


  play(askPlayer, pick).run match {
    case (logs, `Win`) =>
      println(logs.mkString("\n"))
      println("You win")

    case (logs, `Lose`) =>
      println(logs.mkString("\n"))
      println("You lost")
  }







}
