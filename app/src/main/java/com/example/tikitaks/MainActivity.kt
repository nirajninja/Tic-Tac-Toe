package com.example.tikitaks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val boardCells=Array(3){ arrayOfNulls<ImageView>(3)}
    var board=Board()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadBoard()
        button_restart.setOnClickListener{
            board= Board()
            text_view_result.text=""
            mapBOardToUi()
        }

    }
    private fun mapBOardToUi(){
        for(i in board.board.indices)
        {
            for(j in board.board.indices)
            {
                when(board.board[i][j]){
                    Board.PLAYER->{
                        boardCells[i][j]?.setImageResource(R.drawable.circle)
                        boardCells[i][j]?.isEnabled=false
                    }
                    Board.COMPUTER->{

                        boardCells[i][j]?.setImageResource(R.drawable.cross)
                        boardCells[i][j]?.isEnabled=false


                    }
                    else->{

                        boardCells[i][j]?.setImageResource(0)
                        boardCells[i][j]?.isEnabled=true

                    }
                }

            }
        }
    }

    private fun loadBoard(){
        for(i in boardCells.indices){
            for(j in boardCells.indices)
            {
                boardCells[i][j]= ImageView(this)
                boardCells[i][j]?.layoutParams= GridLayout.LayoutParams().apply{
                    rowSpec=GridLayout.spec(i)
                    columnSpec=GridLayout.spec(j)
                    width=200
                    height=200
                    bottomMargin=10
                    topMargin=10
                    leftMargin=10
                    rightMargin=10

                }
                boardCells[i][j]?.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary))
                boardCells[i][j]?.setOnClickListener(CellClickListener(i,j))
                layout_board.addView(boardCells[i][j])
            }
        }
    }
    inner class CellClickListener(private val i:Int,private val j:Int): View.OnClickListener{
        override fun onClick(p0: View?) {
            if( !board.isGameOver){


                val cell=Cell(i,j)
                board.placeMove(cell,Board.PLAYER)

                    board.minimax(0,Board.COMPUTER)
                board.computerMove?.let{
                    board.placeMove(it,Board.COMPUTER)

                }



                mapBOardToUi()
            }
            when{
                board.hasComputerWon()->text_view_result.text="Computer Won"
                board.hasPlayerWon()->text_view_result.text="Player Won"
                board.isGameOver->text_view_result.text="Game Tied"
            }



        }

    }

}
