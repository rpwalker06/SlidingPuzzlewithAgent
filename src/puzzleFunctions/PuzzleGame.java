package puzzleFunctions;

/*
 * PuzzleGame.java
 *
 * Charita Brent
 * @02177832
 * Art. Intelligence
 * SYCS 660
 * Eight Puzzle Program
 */
//package AIEightPuzzleGame;

import java.util.*;



public class PuzzleGame {

    public static final int BLANK = 0;
    //total goal state for puzzle
    static int[][] goalSpaces =
    {/*The matrix in the proposal is this.  It appears that there will
      *two blanks and not one blank and a zero number, so I will change it
        */
       // {{0,1,2},{3,4,5},{6,7,0}};
        {1,4,7},
        {2,5,8},
        {3,6,0}};

    //matrix for puzzle
    /*public static */int[][] spaces;


    /** Creates a new instance of Game */
    public PuzzleGame() 
    {
        //declare it is a 3X3
        spaces = new int [3][3];
        for(int row =0; row < 3; ++row/*++*/)
            for(int col =0; col< 3; ++col)
                spaces [row][col] = goalSpaces[row][col];
    }

   //New Game PuzzleSpaces give space positions
    public PuzzleGame(int[][] puzzleSpaces)
    {
       spaces = new int [3][3];
       for(int row = 0; row < 3; row++)
           for(int col=0; col<3; col++)
               spaces [col][row] = puzzleSpaces[col][row];
   }
   //builds new puzzlestates from the previous puzzle states
   public PuzzleGame(PuzzleGame build)
   {
       spaces = new int [3][3];
       for(int row = 0; row < 3; row++)
           for(int col = 0; col < 3; col++)
               spaces [col][row] = build.spaces[col][row];
   }


   public boolean isSpacesCorrect()
   {
       for(int row = 0; row < 3; ++row/*++*/)
 //      {
           for(int col = 0; col < 3; ++col/*++*/)
 //          {
               if (spaces[row][col] != goalSpaces[row][col])
  //             {
                   return false;
  //             }
               /*else
               {
                   return true;
               }*/
  //         }
  //    }
       return true;
   }
      //Pinpoints the tile.  Spaceloc is the identifier
   public SpaceLocal getSpaceLocal(int spaceloc)
   {
       for(int row = 0; row < 3; ++row/*++*/)
    //   {
           for(int col = 0; col < 3; ++col/*++*/)
      //      {
                if (spaces[row][col] == spaceloc)
       //         {
                    return new SpaceLocal(row, col);
        //        }

         //  }
      // }
       return new SpaceLocal(-1, -1);

 }
   public SpaceLocal getSpaceLocal(int spaceloc, int[][] puzzleSpaces)
   {
       for(int row = 0; row < 3; row++)
  //     {
           for(int col = 0; col < 3; col++)
   //        {
             if(puzzleSpaces[row][col] == spaceloc)
    //         {
                return new SpaceLocal(row, col);
     //        }

     //      }
     //  }
          return new SpaceLocal(-1,-1);

   }
   //Check to see if the xy pointed tile can be moved
   public boolean moveCheck(int spaceloc)
   {
       int xVal;
       int yVal;
       SpaceLocal empty = getSpaceLocal(0);
       SpaceLocal sp_locate = getSpaceLocal(spaceloc);
       //I made the x and y values abs to try to rid the if statement
         xVal = empty.x - sp_locate.x;
         yVal = empty.y - sp_locate.y;
      /*Can I do this after making everything absolute*/
          if(!((xVal ==0 && (yVal ==1 || yVal ==-1))||(yVal == 0 && (xVal ==1 ||xVal==-1))))
           return false;
        else
            return true;


   }
         //  Only one tile can move at time.


   public synchronized void willMove(int spaceloc)
   {
       if(moveCheck(spaceloc))
       {
       SpaceLocal empty = getSpaceLocal(0);
       SpaceLocal sp_locate = getSpaceLocal(spaceloc);
       spaces[empty.x][empty.y] = spaceloc; //Swap the empty with the numbered space
       spaces[sp_locate.x][sp_locate.y] = 0;
   }
 } //randomize the
   public void randomize(int amount)
   {
      // int amount;
       Random r = new Random(System.currentTimeMillis());

       for(int i = 0; i < amount; ++i/*++*/)
           willMove(r.nextInt() % 8 + 1);


   }

   public int where_is_Space(int row, int col)
   {
       return spaces[row][col];
   }
   //Give the heuristic ManhattanDistance taking all the
   //misplaced spaces and adding them together
   public int cityBlockDistance()
   {
       int sum = 0;

       for(int i = 1; i <= 8; i++)
       {
        SpaceLocal spacedist = getSpaceLocal(i);
       //need the space and its goalSpace location
       SpaceLocal goaldist = getSpaceLocal(i, goalSpaces);

       int xVal = spacedist.x - goaldist.x;
       int yVal = spacedist.y - goaldist.y;


         if(xVal < 0)
          xVal = -xVal;
        if(yVal < 0)
           yVal = -yVal;


       sum = sum + xVal + yVal;
       }
           return sum;
   }

//Console Printout
   public void printOut(int move_no)
   {
       String header_line = "+=============Move No: " + move_no  + "==============+";
       String footer_line = "=++++++++++++++++++++++++++++++++++++=";
       
       System.out.println(header_line);
       String indent = "\t";
              
       for(int col = 0; col < 3; col++)
       {
           for(int row = 0; row < 3; row++)
           {
               //if the spaces are not null, print them out
               
               if(spaces[row][col] > 0)
               {
                   System.out.print(spaces[row][col] + " ");
               }
               else
               {
               System.out.print("  ");
               }
               
           }
           System.out.println();
       }
       System.out.println(footer_line);
   }
 }
