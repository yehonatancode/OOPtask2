package Coords;

import Geom.Point3D;

public class MyCoords implements coords_converter{
	Point3D temp;
	private double earthrhRadius = 6371000;
	/** computes a new point which is the gps point transformed by a 3D vector (in meters)*/
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
double lonNorm = Math.cos((gps.x()*Math.PI)/180);
		
		double lat_change = Math.asin(local_vector_in_meter.x()/lonNorm/earthrhRadius);
		lat_change = (lat_change/Math.PI) * 180;
		lat_change = lat_change + gps.x();
		
		double lon_change = Math.asin(local_vector_in_meter.y()/lonNorm/earthrhRadius);
		lon_change = (lon_change/Math.PI) * 180;
		lon_change = lon_change + gps.y();
		
		Point3D p = new Point3D(lat_change, lon_change, gps.z()+local_vector_in_meter.z());
		return p;
	}
	/** computes the 3D distance (in meters) between the two gps like points */
	public double distance3d(Point3D gps0, Point3D gps1) {
		double lat_diff = Math.toRadians(gps0.x() - gps1.x());
		double lon_diff = Math.toRadians(gps0.y() - gps1.y());
		double distance = Math.sin(lat_diff/2)*Math.sin(lat_diff/2) + 
				Math.cos(Math.toRadians(gps0.x()))*Math.cos(Math.toRadians(gps1.x()))
				*Math.sin(lon_diff/2) * Math.sin(lon_diff/2);
		distance = 2 * Math.atan2(Math.sqrt(distance), Math.sqrt(1-distance));
		return distance*earthrhRadius;
	}
	/** computes the 3D vector (in meters) between two gps like points */
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
double lonNorm = Math.cos((gps0.x()*Math.PI)/180);
		
		double lat_diff = gps0.x() - gps1.x();
		double lon_diff = gps0.y() - gps1.y();
		double lat_radian = (Math.PI*lat_diff) / 180;
		double lon_radian = (Math.PI*lon_diff) / 180;
		lat_radian = (Math.sin(lat_radian))*lonNorm*earthrhRadius;
		lon_radian = (Math.sin(lon_radian))*lonNorm*earthrhRadius;
		Point3D p = new Point3D(lat_radian,lon_radian,gps1.z() - gps0.z());
		return p;
	}
	/** computes the polar representation of the 3D vector be gps0-->gps1 
	 * Note: this method should return an azimuth (aka yaw), elevation (pitch), and distance*/
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
		double dX = gps0.x() - gps1.x();
		double dY = gps0.y() - gps1.y();
		double dZ = gps0.z() - gps1.z();
		double [] azimuth = new double [3]; 
				azimuth[2] = distance3d(gps0, gps1); // computes the distance beetwean 2 points.
				azimuth[1] = Math.toDegrees(Math.asin((gps1.z()-gps0.z())/azimuth[2])); // computes the elevation beetwean the 2 points.
				azimuth[0]=  getAzimuth(gps0, gps1); // computes the azimuth beetwean 2 points.
				return azimuth;
	}
	// this fuction computes the azimuth between two points.
		private double getAzimuth(Point3D gps1, Point3D gps2) {
			double azimuth;
			double dY = gps2.y() - gps1.y();
			azimuth = Math.atan2(Math.sin(dY) * Math.cos(gps2.x()) , 
					Math.cos(gps1.x()) * Math.sin(gps2.x()) - (Math.sin(gps1.x()) * Math.cos(gps2.x()) * Math.cos(dY)));
			return Math.toDegrees(azimuth) < 0 ? Math.toDegrees(azimuth)+360.00 : Math.toDegrees(azimuth);
		}
	/*
	 * return true iff this point is a valid lat, lon , lat coordinate: [-180,+180],[-90,+90],[-450, +inf]
	 * @param p
	 * @return
	 */
	public boolean isValid_GPS_Point(Point3D p) {
		if(p.x() > -90 && p.x() < 90 && p.y() > -90 && p.y() < 90 && p.z()<6000 && p.z() > -400)
			return true;
			else throw new RuntimeException("** Error: Point3D coordinates wrong **");
				
		}

	}
