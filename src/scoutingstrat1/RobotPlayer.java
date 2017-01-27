package scoutingstrat1;

import battlecode.common.*;

public strictfp class RobotPlayer {
    static RobotController rc;

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * If this method returns, the robot dies!
    **/
    public static void run(RobotController rc) throws GameActionException {

        // This is the RobotController object. You use it to perform actions from this robot,
        // and to get information on its current status.
        RobotPlayer.rc = rc;

        // Here, we've separated the controls into a different method for each RobotType.
        // You can add the missing ones or rewrite this into your own control structure.
        switch (rc.getType()) {
            case ARCHON:
                runArchon();
                break;
            case GARDENER:
                runGardener();
                break;
            case SOLDIER:
                runSoldier();
                break;
            case LUMBERJACK:
                runLumberjack();
                break;
            case SCOUT:
                runScout();
                break;
            case TANK:
                runTank();
                break;
        }
	}

    static void runArchon() throws GameActionException {
    	
    	System.out.println(RobotType.ARCHON.sensorRadius);
    	
    	MapLocation toSpawnGardnerLoc;
    	boolean offMapNorth = false;
    	boolean offMapEast = false;
    	boolean offMapWest = false;
    	boolean offMapSouth = false;
    	MapLocation toMoveLocation;
    	int moveAwayCounter = 0;
    	
        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {
            	
            	toSpawnGardnerLoc = rc.getLocation().add(Direction.getWest(), 3);
            	rc.setIndicatorDot(toSpawnGardnerLoc, 255, 255, 255);
            	toMoveLocation = rc.getLocation();
            	
            	if (rc.getRoundNum() < 200){
            		if ( rc.canHireGardener(Direction.getWest()) ){
            			rc.hireGardener(Direction.getWest());
            			moveAwayCounter = 20;
            		}
            		if (moveAwayCounter > 0){
                		tryMove(Direction.getEast(), 20, 6);
                		moveAwayCounter--;
                	}
            	}
            	
            	
            	
            	if (rc.onTheMap(toSpawnGardnerLoc, 3)){
            		if (rc.senseNearbyTrees(toSpawnGardnerLoc, 3, Team.NEUTRAL).length == 0 && 
            										rc.senseNearbyTrees(toSpawnGardnerLoc, 3, rc.getTeam()).length == 0 &&
            										rc.senseNearbyRobots(toSpawnGardnerLoc, 3, rc.getTeam()).length == 0){
            			if ( rc.canHireGardener(Direction.getWest()) ){
                			rc.hireGardener(Direction.getWest());
                		}
            		}
            		
            		offMapNorth = false;
            		offMapEast = false;
            		offMapWest = false;
            		offMapSouth = false;
            	}
            	else{
            		if (!rc.onTheMap(toSpawnGardnerLoc.add(Direction.getNorth(), 3))){
            			offMapNorth = true;
            		}
            		if (!rc.onTheMap(toSpawnGardnerLoc.add(Direction.getEast(), 3))){
            			offMapEast = true;
            		}
            		if (!rc.onTheMap(toSpawnGardnerLoc.add(Direction.getSouth(), 3))){
            			offMapSouth = true;
            		}
            		if (!rc.onTheMap(toSpawnGardnerLoc.add(Direction.getWest(), 3))){
            			offMapWest = true;
            		}
            		
            	}
            	
            	if (offMapNorth || offMapEast || offMapWest || offMapSouth){

            		if (offMapNorth){
            			toMoveLocation = toMoveLocation.add(Direction.getSouth());
            		}
            		if (offMapEast){
            			toMoveLocation = toMoveLocation.add(Direction.getWest());
            		}
            		if (offMapWest){
            			toMoveLocation = toMoveLocation.add(Direction.getEast());
            		}
            		if (offMapSouth){
            			toMoveLocation = toMoveLocation.add(Direction.getNorth());
            		}
            		if (rc.getLocation().directionTo(toMoveLocation) != null){
            			tryMove(rc.getLocation().directionTo(toMoveLocation));
            		}else{
            			wander();
            		}
            	}
            	else{
            		wander();
            	}

            	
            	
            	
            	
            	if (rc.getTeamBullets() > 10000){
            		rc.donate(10000);
            	}
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception");
                e.printStackTrace();
            }
        }
    }

	static void runGardener() throws GameActionException {
		/*
    	 * 0- 60 degree
    	 * 1- 120 degree
    	 * ...
    	 * 4 - 300 degree
    	 */
    	int directionToPlant = 0;

        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {
            	
            	if(rc.getRoundNum() < 3000){
            		
            		if (  rc.canBuildRobot(RobotType.LUMBERJACK, Direction.getEast())  ){
            			rc.buildRobot(RobotType.LUMBERJACK, Direction.getEast());
            		}
            		
            	}

            	//attempts to water nearby tree
            	TreeInfo[] nearbyTrees = rc.senseNearbyTrees(2, rc.getTeam());
            	for (TreeInfo t : nearbyTrees){
           			if (  rc.canWater(t.getID()) && t.getHealth() < t.getMaxHealth()*0.9 ){
           				rc.water(t.getID());

           				break;
            		}

            	}
            	
            	if(rc.hasTreeBuildRequirements()){
	            	if (rc.canPlantTree( new Direction( (float)Math.PI*(directionToPlant+1)/3) )){
	            		rc.plantTree(new Direction( (float)Math.PI*(directionToPlant+1)/3));
	            	}else{
	            	}
            	}
            	directionToPlant = (directionToPlant+1)%5;
            	
               
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception");
                e.printStackTrace();
            }
        }
    }

    static void runSoldier() throws GameActionException {


        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {

               
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception");
                e.printStackTrace();
            }
        }
    }

    static void runLumberjack() throws GameActionException {

    	TreeInfo[] toCut;
    	RobotInfo[] nearbyRobots;

    	Direction randomMovingDirection = randomDirection();
    	
    	boolean shouldMove = true;
    	
        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {
            	shouldMove = true;
            	
            	//attack code
           		nearbyRobots = rc.senseNearbyRobots();
           		
           		for (RobotInfo r : nearbyRobots){
           			if (r.getTeam() != rc.getTeam()){
               			if (rc.getLocation().isWithinDistance(r.getLocation(), GameConstants.LUMBERJACK_STRIKE_RADIUS)){
               				System.out.println("Within distance");
               				if (rc.canStrike()){
               					rc.strike();

               					System.out.println("Attacked");
               					break;
               				}
               			}
           			}
           		}
            	
            	//cut tree code
            	toCut = rc.senseNearbyTrees();
            	
            	if (toCut.length > 0){
	            	for (TreeInfo t : toCut){
	            		if (t.getTeam() != rc.getTeam()){
	            			if (rc.canShake(t.getID())){
	            				rc.shake(t.getID());
	            			}
	            			if (rc.canChop(t.getID())){
	                			rc.chop(t.getID());
	                			shouldMove = false;
	                			break;
	                		}
	            			else{
	                			tryMove(rc.getLocation().directionTo(t.getLocation()));
	                			break;
	                		}
	            		}
	            	}
            	}
            	
            	//random movement code (if not cutting a tree
           		if (shouldMove && !rc.hasMoved()){
           			if (!tryMove(randomMovingDirection)){
               			randomMovingDirection = randomDirection();
               		}
           		}


               
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception");
                e.printStackTrace();
            }
        }
    }
    
    static void runScout() throws GameActionException {


        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {

               
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception");
                e.printStackTrace();
            }
        }
    }
    
    static void runTank() throws GameActionException {


        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {

               
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception");
                e.printStackTrace();
            }
        }
    }
    
    
    
//-----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a random Direction
     * @return a random Direction
     */
    static Direction randomDirection() {
        return new Direction((float)Math.random() * 2 * (float)Math.PI);
    }

    /**
     * Attempts to move in a given direction, while avoiding small obstacles directly in the path.
     *
     * @param dir The intended direction of movement
     * @return true if a move was performed
     * @throws GameActionException
     */
    static boolean tryMove(Direction dir) throws GameActionException {
        return tryMove(dir,20,3);
    }

    /**
     * Attempts to move in a given direction, while avoiding small obstacles direction in the path.
     *
     * @param dir The intended direction of movement
     * @param degreeOffset Spacing between checked directions (degrees)
     * @param checksPerSide Number of extra directions checked on each side, if intended direction was unavailable
     * @return true if a move was performed
     * @throws GameActionException
     */
    static boolean tryMove(Direction dir, float degreeOffset, int checksPerSide) throws GameActionException {
    	
    	
        // First, try intended direction
        if (rc.canMove(dir)) {
            rc.move(dir);
            return true;
        }

        // Now try a bunch of similar angles
        boolean moved = false;
        int currentCheck = 1;

        while(currentCheck<=checksPerSide) {
            // Try the offset of the left side
            if(rc.canMove(dir.rotateLeftDegrees(degreeOffset*currentCheck))) {
                rc.move(dir.rotateLeftDegrees(degreeOffset*currentCheck));
                return true;
            }
            // Try the offset on the right side
            if(rc.canMove(dir.rotateRightDegrees(degreeOffset*currentCheck))) {
                rc.move(dir.rotateRightDegrees(degreeOffset*currentCheck));
                return true;
            }
            // No move performed, try slightly further
            currentCheck++;
        }

        // A move never happened, so return false.
        return false;
    }

    /**
     * A slightly more complicated example function, this returns true if the given bullet is on a collision
     * course with the current robot. Doesn't take into account objects between the bullet and this robot.
     *
     * @param bullet The bullet in question
     * @return True if the line of the bullet's path intersects with this robot's current position.
     */
    static boolean willCollideWithMe(BulletInfo bullet) {
        MapLocation myLocation = rc.getLocation();

        // Get relevant bullet information
        Direction propagationDirection = bullet.dir;
        MapLocation bulletLocation = bullet.location;

        // Calculate bullet relations to this robot
        Direction directionToRobot = bulletLocation.directionTo(myLocation);
        float distToRobot = bulletLocation.distanceTo(myLocation);
        float theta = propagationDirection.radiansBetween(directionToRobot);

        // If theta > 90 degrees, then the bullet is traveling away from us and we can break early
        if (Math.abs(theta) > Math.PI/2) {
            return false;
        }

        // distToRobot is our hypotenuse, theta is our angle, and we want to know this length of the opposite leg.
        // This is the distance of a line that goes from myLocation and intersects perpendicularly with propagationDirection.
        // This corresponds to the smallest radius circle centered at our location that would intersect with the
        // line that is the path of the bullet.
        float perpendicularDist = (float)Math.abs(distToRobot * Math.sin(theta)); // soh cah toa :)

        return (perpendicularDist <= rc.getType().bodyRadius);
    }
    
    static void wander() throws GameActionException{
    	Direction movingDir = randomDirection();
    	if (rc.canMove(movingDir) && !rc.hasMoved()){
    		rc.move(movingDir);
    	}
    }
}
