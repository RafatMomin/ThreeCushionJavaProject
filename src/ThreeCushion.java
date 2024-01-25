package hw2;

import api.PlayerPosition;
import api.BallType;

/**
 * Class that models the game of three-cushion billiards.
 * 
 * @author Rafat Momin
 * 
 */


/*
 * Warning: speccheker is not working, it is showing
 * java.lang.NoSuchMethodError: 'void hw2.ThreeCushion.cueStickStrike(api.BallType)'
 * 
 * Note: Although I've implemented the function properly.
 * kindly run my code in your IDE and than starting the grading
 * 
 */
public class ThreeCushion {

	// TODO: EVERTHING ELSE GOES HERE
	// Note that this code will not compile until you have put in stubs for all
	// the required methods.

	// The method below is provided for you and you should not modify it.
	// The compile errors will go away after you have written stubs for the
	// rest of the API methods.

	/*
	 * who wins the lag
	 */
	private PlayerPosition lagWinner; 
	
	/*
	 * who currently playing the innings
	 */
	private PlayerPosition inningsPlayer;
	
	/*
	 * current cue ball
	 */
	private BallType cueBall;
	/*
	 * points to win
	 */
	private int pointsToWin;
	/*
	 * counts innings
	 */
	private int inningsCount;
	/*
	 * score of playerA
	 */
	private int playerScoreA;
	/*
	 * score of playerB
	 */
	private int playerScoreB;
	/*
	 * game started
	 */
	private boolean isGameStarted;
	/*
	 * inningsstarted
	 */
	private boolean isInningStarted;
	/*
	 * check if the shot is breakshot
	 */
	private boolean isBreakShot;
	/*
	 * check if the shot is bankshot
	 */
	private boolean isBankShot;
	/*
	 * check if the shprt is started or not
	 */
	private boolean isShotStarted;
	/*
	 * check if the shot is invalid or not
	 */
	private boolean invalidShot;
	/*
	 * current firstStrikeBall
	 */
    private BallType firstStrikeBall;
    /*
     * balltype secondStrikeBall
     */
    private BallType secondStrikeBall;
    /*
     * current cushionImpactCount as integer
     */
    private int cushionImpactCount;
    /*
     * check if foul happened or not
     */
    private boolean isFoul;
	
	
	/*Creates a new game of three-cushion billiards with a given lag winner and the predetermined number of points required to win the game. 
	 * 
	 * 
	 * @param takes two parameters lagWinner and integer pointsToWin
	 * @param how much point to win
	 */
	public ThreeCushion(PlayerPosition lagWinner, int pointsToWin) {
		this.lagWinner = lagWinner;
		this.pointsToWin = pointsToWin;
		inningsCount = 1;
	}
	
	
	/* change the playerposition by using getAlternatePlayer mehtod
	 * 
	 * @param take one parameter for PlayerPosition player
	 * @return PlayerPosition for playerB or playerA 
	 */
	public PlayerPosition getAlternatePlayer(PlayerPosition player) {
		if(player == PlayerPosition.PLAYER_A)
			return PlayerPosition.PLAYER_B;
		else
			return PlayerPosition.PLAYER_A;
	}
	
	
	/*BallType getAlternateBall mehtod helps to swipe the ball color either it is WHITE or YELLOW
	 * 
	 * @param take Balltype ball as instance variable
	 * @return the ball's color whether it is YELLOW or WHITE
	 */
	public BallType getAlternateBall(BallType ball) {
		if(ball == BallType.WHITE)
			return BallType.YELLOW;
		else
			return BallType.WHITE;
	}
	
	
	/*lagWinnerChooses mehtod identifies who took the shot
	 * 
	 * @param takes two parameters boolean selfBreak and BallType CueBall
	 * @param identifies
	 */
	public void lagWinnerChooses(boolean selfBreak, BallType CueBall) {
		
		if(isGameStarted)
			return;
		
		if(selfBreak) {
			inningsPlayer = lagWinner;
			this.cueBall = CueBall;
		}
		else {
			inningsPlayer = getAlternatePlayer(lagWinner);
			this.cueBall = getAlternateBall(CueBall);
		}
		
		isGameStarted = true;
		isBreakShot = true;
		
	}
	
	
	/* indicates the player's cue ball has struck the given ball.
	 *@param Balltype ball 
	 */
	public void cueStickStrike​(BallType ball) {
		if (!isGameStarted || isGameOver())
            return;

        if (isShotStarted) {
            foul();
            return;
        }

        if ((ball == BallType.YELLOW || ball == BallType.WHITE) && ball == cueBall) {
            invalidShot = false;
            isBankShot = true;
        } else
        	foul();


        isShotStarted = true;
        isInningStarted = true;
        cushionImpactCount = 0;
	}
	
	
	/*Indicates the given ball has impacted the given cushion.
	 * takes one parameter BallType ball local variable 
	 * returns nothing as it is a void
	 */
	public void cueBallStrike​(BallType ball) {
		if (!isGameStarted || isGameOver())
            return;

        if (firstStrikeBall == null) {
            firstStrikeBall = ball;
            if (!isFoul && isBreakShot && ball != BallType.RED) {
                foul();
            }
            if (cushionImpactCount < 3)
            	isBankShot = false;
        } else if (firstStrikeBall != ball) {
            secondStrikeBall = ball;
            if (cushionImpactCount < 3)
            	invalidShot = true;
        }
	}
	
	
	/*Indicates the given ball has impacted the given cushion.
	 * 
	 * no param
	 * @returns nothing as it is a void
	 */
	public void cueBallImpactCushion() {
		if (!isGameStarted || isGameOver())
            return;

        if (!isFoul && isBreakShot && firstStrikeBall == null)
            foul();

        ++cushionImpactCount;
	}
	
	
	/*The shot cannot end before it has started with a call to cueStickStrike. If this method is called before cueStickStrike, it should be ignored.

A shot cannot end before the start of a shot. If this method is called before the start of a shot (i.e., cueStickStrike() is called), it should do nothing.

If this method is called after the game has ended, it should do nothing.
	 * takes no parameter
	 * returns nothing as it is a void
	 */
	public void endShot() {
		if (!isShotStarted || isGameOver())
            return;

        if (!isFoul && !invalidShot && secondStrikeBall != null) {
            if (inningsPlayer == PlayerPosition.PLAYER_A)
            	playerScoreA++;
            else
            	playerScoreB++;
            
        } else {
            if (!isFoul) {
                ++inningsCount;
                inningsPlayer = getAlternatePlayer(inningsPlayer);
                cueBall = getAlternateBall(cueBall);
            }
            isBankShot = false;
        }

        isInningStarted = false;
        isShotStarted = false;
        isBreakShot = false;
        isFoul = false;
	}
	
	
    /*A foul immediately ends the player's inning, even if the current shot has not yet ended. When a foul is called, the player does not score a point for the shot.
A foul may also be called before the inning has started. In that case the player whose turn it was to shot has their inning forfeited and the inning count is increased by one.

No foul can be called until the lag player has chosen who will break (see lagWinnerChooses()). If this method is called before the break is chosen, it should do nothing.

If this method is called after the game has ended, it should do nothing.
     * takes no parameter
     * returns nothing as it is a void
     */
	public void foul() {
		if (!isGameStarted || isGameOver())
			return;

       inningsPlayer = getAlternatePlayer(inningsPlayer);
       cueBall = getAlternateBall(cueBall);
       isFoul = true;
       ++inningsCount;
       isInningStarted = false;
	}
	
	
	/* getInning method 
	 * takes no parameter
	 * returns inningsCount
	 */
	public int getInning() {
		return inningsCount;
	}
	
	
	/*int getPlayerBScore method gets the score for player A
	 * takes no parameter
	 * returns playerScoreA
	 */
	public int getPlayerAScore() {
		return playerScoreA;
	}
	
	
	/* int getPlayerBScore method gets the score for player B
	 * takes no parameter
	 * returns playerScoreB
	 */
	public int getPlayerBScore() {
		return playerScoreB;
	}
	
	
	/*getInningPlayer method identifies the innings player
	 * takes no parameter
	 * returns inningsPlayer
	 */
	public PlayerPosition getInningPlayer() {
		return inningsPlayer;
	}
	
	
	/*
	 * Returns true if the game is over (i.e., one of the players has reached the designated number of points to win).
	 * takes no parameter
	 * @returns true if the game is over, false otherwise
	 */
	public boolean isGameOver() {
		if(playerScoreA == pointsToWin || playerScoreB == pointsToWin)
			return true;
		
		return false;
	}
	
	
	/*Returns true if the shooting player has taken their first shot of the inning. The inning starts at the beginning of the shot (i.e., the shot may not have ended yet). 
	 * takes no parameter
	 * @returns true if the inning has started, false otherwise
	 */
	public boolean isInningStarted() {
		return isInningStarted;
	}
	
	
	/*Gets the cue ball of the current player. If this method is called in between innings, the cue ball should be the for the player of the upcoming inning. If this method is called before the lag winner has chosen a cue ball, the cue ball is undefined (this method may return anything). 
	 * takes no parameter
	 * @returns the player's cue ball
	 */
	public BallType getCueBall() {
		return cueBall;
	}
	
	
	/* true if and only if this is the break shot 
	 * takes no parameter
	 * @returns true if this is the break shot, false otherwise
	 */
	public boolean isBreakShot() {
		return isBreakShot;
	}
	
	
	/*A bank shot is when the cue ball impacts the cushions at least 3 times and then strikes both object balls.
	 * takes no parameter
	 * @returns true if shot was a bank shot, false otherwise
	 */
	public boolean isBankShot() {
		return isBankShot;
	}
	
	
	/*
	 * true if a shot has been taken (see cueStickStrike()), but not ended (see endShot()).
	 * @param takes no parameters
	 * @returns true if the shot has been started, false otherwise
	 */
	public boolean isShotStarted() {
		return isShotStarted;
	}

	
	/**
	 * Returns a one-line string representation of the current game state. The
	 * format is:
	 * <p>
	 * <tt>Player A*: X Player B: Y, Inning: Z</tt>
	 * <p>
	 * The asterisks next to the player's name indicates which player is at the
	 * table this inning. The number after the player's name is their score. Z is
	 * the inning number. Other messages will appear at the end of the string.
	 * 
	 * @return one-line string representation of the game state
	 */
	public String toString() {
		String fmt = "Player A%s: %d, Player B%s: %d, Inning: %d %s%s";
		String playerATurn = "";
		String playerBTurn = "";
		String inningStatus = "";
		String gameStatus = "";
		if (getInningPlayer() == PlayerPosition.PLAYER_A) {
			playerATurn = "*";
		} else if (getInningPlayer() == PlayerPosition.PLAYER_B) {
			playerBTurn = "*";
		}
		if (isInningStarted()) {
			inningStatus = "started";
		} else {
			inningStatus = "not started";
		}
		if (isGameOver()) {
			gameStatus = ", game result final";
		}
		return String.format(fmt, playerATurn, getPlayerAScore(), playerBTurn, getPlayerBScore(), getInning(),
				inningStatus, gameStatus);
	}
}


