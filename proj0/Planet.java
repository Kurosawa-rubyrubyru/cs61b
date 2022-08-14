public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
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
        double dx = xxPos - p.xxPos;
        double dy = yyPos - p.yyPos;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    public double calcForceExertedBy(Planet p) {
        double distance = this.calcDistance(p);
        double force = (6.67 * Math.pow(10, -11) * mass * p.mass) / (distance * distance);
        return force;
    }

    public double calcForceExertedByX(Planet p) {
        double force = this.calcForceExertedBy(p);
        double distance = this.calcDistance(p);
        double dx = p.xxPos - xxPos;
        double xforce = (force * dx) / distance;
        return xforce;
    }

    public double calcForceExertedByY(Planet p) {
        double force = this.calcForceExertedBy(p);
        double distance = this.calcDistance(p);
        double dy = p.yyPos - yyPos;
        double yforce = (force * dy) / distance;
        return yforce;
    }

    public double calcNetForceExertedByX(Planet[] ps) {
        double sumForce = 0;
        for (Planet p : ps) {
            if (p.equals(this)) {
                continue;
            } else {
                sumForce += this.calcForceExertedByX(p);
            }
        }
        return sumForce;
    }

    public double calcNetForceExertedByY(Planet[] ps) {
        double sumForce = 0;
        for (Planet p : ps) {
            if (p.equals(this)) {
                continue;
            } else {
                sumForce += this.calcForceExertedByY(p);
            }
        }
        return sumForce;
    }

    public void update(double t, double fx, double fy) {
        double dxv = (fx * t) / this.mass;
        double dyv = (fy * t) / this.mass;
        this.xxVel = this.xxVel + dxv;
        this.yyVel = this.yyVel + dyv;

        double dx = this.xxVel * t;
        double dy = this.yyVel * t;
        this.xxPos = this.xxPos + dx;
        this.yyPos = this.yyPos + dy;


    }

    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "./images/" + this.imgFileName);
    }
}

