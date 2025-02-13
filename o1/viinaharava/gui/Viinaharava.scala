package o1.viinaharava.gui

import scala.swing.*
import o1.viinaharava.*
import o1.gui.{Pic,BasicGridDisplay,O1AppDefaults,Escapable}
import o1.gui.swingops.*
import o1.gui.Dialog.*
import scala.util.{Try,Success,Failure}
import scala.language.adhocExtensions // enable extension of Swing classes

////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it’s not necessary
// that you understand or even look at the code in this file.
//////////////////////////////////////////////////////////////

/** The singleton object `Viinaharava` represents the Viinaharava application. The object
  * serves as an entry point for the game, and can be run to start up the user interface.
  *
  * **NOTE TO STUDENTS: In this course, you don’t need to understand how this object works
  * on the inside. It’s enough to know that you can use this file to start the program.** */
object Viinaharava extends SimpleSwingApplication, O1AppDefaults:

  def top = new MainFrame with Escapable:
    this.title = "Viinaharava"
    this.location = Point(50, 50)
    this.menuBar = new MenuBar:
      contents += new Menu("Game"):
        contents += MenuItem(Action("New 8 x 8")( startNewGame(createDefaultBoard) ))
        contents += MenuItem(Action("New...") ( requestBoard().foreach(startNewGame) ))
        contents += Separator()
        contents += MenuItem(Action("Exit")( dispose() ))
    this.resizable = false
    this.startNewGame(this.createDefaultBoard)

    private def studentFriendlyFail(npe: NullPointerException) =
      display("Board initialization was terminated thanks to an old foe: a NullPointerException.\nA likely cause is that class GameBoard accessed one of its instance variables before the variable was initialized.\nSee the stack trace for further details.", RelativeTo(this))
      throw npe

    def createBoard(width: Int, height: Int, boozes: Int) =
      Try( GameBoard(width, height, boozes) ) match
        case Success(newBoard)                  => newBoard
        case Failure(npe: NullPointerException) => studentFriendlyFail(npe)
        case Failure(somethingElse)             => throw somethingElse

    def createDefaultBoard = this.createBoard(8, 8, 12)

    def requestBoard() =
      def askWidth() =      requestInt("Width of the board:",      _ > 0,  "Please enter a positive integer.",     RelativeTo(this))
      def askHeight() =     requestInt("Height of the board:",     _ > 0,  "Please enter a positive integer.",     RelativeTo(this))
      def askBoozeCount() = requestInt("Number of booze glasses:", _ >= 0, "Please enter a non-negative integer.", RelativeTo(this))
      for width <- askWidth(); height <- askHeight(); boozes <- askBoozeCount() yield this.createBoard(width, height, boozes)

    def startNewGame(board: GameBoard) =
      val view = ViinaharavaDisplay(board)
      view.update()
      this.contents = FlowPanel(view)
      this.pack()

  end top


  private class ViinaharavaDisplay(val board: GameBoard) extends BasicGridDisplay[GameBoard, Glass](board, ViinaharavaDisplay.MaxSquareSize):

    val RevealedBoozePic  = load("pictures/empty.png")
    val FullGlassPic      = load("pictures/full.png" )
    val RevealedWaterPics = for danger <- 0 to 8 yield load("pictures/" + danger + ".png")

    def isGameOver = Try( board.isGameOver ).toOption.contains(true)

    private def load(picPath: String): Array[BufferedImage] =
      Pic.asImage(picPath).map( this.scale ).map( Array(_) ).getOrElse(Array.empty)

    def missingElementVisuals = RevealedWaterPics(0)
    def elementVisuals(glass: Glass) = if !glass.isEmpty then FullGlassPic
                                       else if glass.isBooze then RevealedBoozePic
                                       else RevealedWaterPics(glass.dangerLevel)
    val popup = new Popup:
      import PopupAction.*
      this += ElementAction("Drink",          _ => !isGameOver )(drink)
      this += ElementAction("Add booze here", _ => !isGameOver )(  _.pourBooze() )

    def drink(glass: Glass) =
      if !isGameOver then
        this.board.drink(glass)
        if glass.isBooze then
          this.update()
          display("Oh no! You struck booze.", RelativeTo(this))
        else if this.board.isOutOfWater then
          this.update()
          display("You found all the water!", RelativeTo(this))

    def elementClicked(glass: Glass) =
      this.drink(glass)

  end ViinaharavaDisplay

  private object ViinaharavaDisplay:
    val MaxSquareSize = 100

end Viinaharava

