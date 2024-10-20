package org.firstinspires.ftc.teamcode.util.motionprofiles;

public class TrapezoidalMotionProfile {

    private double acc_dt, acc_dist, cruise_dist, dec_time;
    private final double max_vel, max_acc, dist, time, cruise_dt, current;
    private final int negative;

    public TrapezoidalMotionProfile(double max_vel, double max_acc, double current, double target) {
        this.max_acc = max_acc;
        this.current = current;
        this.negative = target < current ? -1 : 1;
        this.dist = Math.abs(target - current);

        acc_dt = max_vel / max_acc;
        double halfway = dist / 2.0;
        acc_dist = 0.5 * max_acc * (acc_dt * acc_dt);

        if(acc_dist > halfway) {
            acc_dt = Math.sqrt(halfway / (0.5 * max_acc));
        }

        acc_dist = 0.5 * max_acc * (acc_dt * acc_dt);

        max_vel = max_acc * acc_dt;
        this.max_vel = max_vel;

        cruise_dist = dist - 2 * acc_dist;
        cruise_dt = cruise_dist / max_vel;
        dec_time = acc_dt + cruise_dt;

        time = acc_dt + cruise_dt + acc_dt;
    }

    public double get(double t) {
        double acc_dist = this.acc_dist;
        double cruise_dist = this.cruise_dist;
        double dec_time = this.dec_time;

        if(t > time) {
            return current + (dist * negative);
        }

        if(t < acc_dt) {
            return current + negative * (0.5 * max_acc * (t * t));
        } else if (t < dec_time) {
            acc_dist = 0.5 * max_acc * (acc_dt * acc_dt);
            double cruise_curr = t - acc_dt;

            return current + negative * (acc_dist + max_vel * cruise_curr);
        } else {
            acc_dist = 0.5 * max_acc * (acc_dt * acc_dt);
            cruise_dist = max_vel * cruise_dt;
            dec_time = t - dec_time;

            return current + negative * (acc_dist + cruise_dist + max_vel * dec_time - (0.5 * max_acc * (dec_time * dec_time)));
        }
    }

}
