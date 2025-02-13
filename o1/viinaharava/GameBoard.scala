package o1.viinaharava

import o1.grid.*
import o1.DoNothing
import scala.util.Random

class GameBoard(width: Int, height: Int, boozeCount: Int) extends Grid[Glass](width, height):

  def initialElements =
    val allLocations = (0 until this.size).map( n => GridPos(n % this.width, n / this.width) )
    allLocations.map( loc => Glass(this, loc) )

  this.placeBoozeAtRandom(boozeCount)

  private def placeBoozeAtRandom(howMany: Int) =
    val allGlasses = this.allElements
    val randomBoozeGlasses = Random.shuffle(allGlasses).take(howMany)
    randomBoozeGlasses.foreach(_.pourBooze())

  def boozeGlasses = this.allElements.filter( _.isBooze )

  def isOutOfWater: Boolean = !this.allElements.exists(glass => !glass.isBooze && !glass.isEmpty)

  def isGameOver = this.isOutOfWater || this.allElements.exists(glass => glass.isBooze && glass.isEmpty)

  def drink(target: Glass): Unit =
    if target.isBooze then
      this.allElements.filter(_.isBooze).foreach(_.empty())
    else
      target.empty()

end GameBoard
