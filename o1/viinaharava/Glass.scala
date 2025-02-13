package o1.viinaharava

import o1.*

class Glass(val board: GameBoard, val location: GridPos):

  private var isFull = true
  private var isWater = true
  private var danger = 0

  def isEmpty = !this.isFull

  def isBooze = !this.isWater

  def dangerLevel = this.danger

  def isSafe = this.danger == 0

  private def isValidPosition(pos: GridPos) = 
    pos.x >= 0 && pos.x < board.width && pos.y >= 0 && pos.y < board.height

  def neighbors: Vector[Glass] =
    val currentX = location.x
    val currentY = location.y
    val possibleNeighbors = Vector(
      GridPos(currentX, currentY - 1),     // up
      GridPos(currentX, currentY + 1),     // down
      GridPos(currentX - 1, currentY),     // left
      GridPos(currentX + 1, currentY),     // right
      GridPos(currentX - 1, currentY - 1), // up-left
      GridPos(currentX + 1, currentY - 1), // up-right
      GridPos(currentX - 1, currentY + 1), // down-left
      GridPos(currentX + 1, currentY + 1)  // down-right
    )
    possibleNeighbors.filter(isValidPosition).map(board.elementAt(_)).toVector

  def pourBooze(): Unit =
    if this.isWater then
      this.isWater = false
      this.isFull = true
      for neighbor <- this.neighbors do
        neighbor.raiseDanger()

  private def raiseDanger() =
    this.danger += 1

  def empty(): Boolean =
    val wasFull = this.isFull
    this.isFull = false
    wasFull

end Glass
