public class Planet {

    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    private static final double G = 6.67e-11;

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double xDist = xxPos - p.xxPos;
        double yDist = yyPos - p.yyPos;
        return Math.sqrt(xDist * xDist + yDist * yDist);
    }

    public double calcForceExertedBy(Planet p) {
        double dist = calcDistance(p);
        return G * mass * p.mass / dist / dist;
    }

    public double calcForceExertedByX(Planet p) {
        double dist = calcDistance(p);
        double displacementX = p.xxPos - xxPos;
        return calcForceExertedBy(p) * displacementX / dist;
    }

    public double calcForceExertedByY(Planet p) {
        double dist = calcDistance(p);
        double displacementY = p.yyPos - yyPos;
        return calcForceExertedBy(p) * displacementY / dist;
    }

    public double calcNetForceExertedByX(Planet[] pArray) {
        double result = 0;
        for (Planet p : pArray) {
            if (!equals(p)) {
                result += calcForceExertedByX(p);
            }
        }
        return result;
    }

    public double calcNetForceExertedByY(Planet[] pArray) {
        double result = 0;
        for (Planet p : pArray) {
            if (!equals(p)) {
                result += calcForceExertedByY(p);
            }
        }
        return result;
    }

    public void update(double dt, double fX, double fY) {
        double aX = fX / mass;
        double aY = fY / mass;
        xxVel += aX * dt;
        yyVel += aY * dt;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
