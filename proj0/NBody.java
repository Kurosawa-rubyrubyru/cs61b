public class NBody {

    public static double readRadius(String file) {
        //对象还没有创建，所以在测试的静态上下文内不能调用非静态方法
//        在静态的方法中不能直接调用非静态的变量或属性。因为一个类的静态方法在这个Class文件被加载之后，
//        就可以由这个Class类型对象来调用，而非静态的方法需要一个实例对象，有可能还未被创建，所以为了避免
//        在静态方法中调用一个还不存在的实例对象的非静态方法，编译期就会直接阻止这个行为。
        In planets_file = new In(file);
        planets_file.readInt();
        double Radius = planets_file.readDouble();
        return Radius;
    }

    public static Planet[] readPlanets(String file) {
        In planets_file = new In(file);
        int sum_planet = planets_file.readInt();
        Planet[] new_planets = new Planet[sum_planet];
        planets_file.readDouble();
        for (int i = 0; i < sum_planet; i += 1) {
            Planet new_planet = new Planet(planets_file.readDouble(), planets_file.readDouble(),
                    planets_file.readDouble(), planets_file.readDouble(), planets_file.readDouble(), planets_file.readString());
            new_planets[i] = new_planet;
        }
        return new_planets;
    }

    public static void main(String[] args) {

        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);

        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        In planets_file = new In(filename);
        int sum_planet = planets_file.readInt();
        StdDraw.setScale(radius * (-1), radius);
        StdDraw.clear();
        String imageToDraw = "images/starfield.jpg";
        StdDraw.picture(0, 0, imageToDraw);

        for (Planet p : planets) {
            p.draw();
        }

        //drawing gif
        StdDraw.enableDoubleBuffering();
        double[] xForce = new double[sum_planet];
        double[] yForce = new double[sum_planet];
        for (int t = 0; t <= T; t += dt) {
            for (int i = 0; i < planets.length; i += 1) {
                xForce[i] = planets[i].calcNetForceExertedByX(planets);
                yForce[i] = planets[i].calcNetForceExertedByY(planets);

            }
            for (int i = 0; i < planets.length; i += 1) {
                planets[i].update(dt, xForce[i], yForce[i]);
            }
            StdDraw.picture(0, 0, imageToDraw);
            for (Planet p : planets) {
                p.draw();
            }
            StdDraw.show(); //用于更新画布
            StdDraw.pause(10);
        }
        System.out.println(sum_planet);
        System.out.println(radius);
        for (Planet p : planets) {
            System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", p.xxPos, p.yyPos, p.xxVel, p.yyVel, p.mass, p.imgFileName);
        }
    }
}
