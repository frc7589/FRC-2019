package frc.util;

public final class RangeMapper {
	private double origA,origB,targA,targB;
	public RangeMapper(double oA, double oB, double tA, double tB){
		origA = oA; origB = oB; targA = tA; targB = tB;
	}
	public RangeMapper(double tA, double tB) {
		origA = -1.0; origB = 1.0; targA = tA; targB = tB;
	}
	public double mapRange(double s) throws IllegalArgumentException{
		if( !inRange(s,origA,origB) ) {
			throw new IllegalArgumentException("s not in available range");
		}
		return targA + ((s - origA)*(targB - targA))/(origB - origA);
	}
	
	private static boolean inRange(double s, double rA, double rB) {
		double a = Math.min(rA, rB), b = Math.max(rA, rB);
		return ( s >= a && s <= b );
	}
}