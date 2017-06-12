package com.sva.common;

import java.util.List;

/**
 * @ClassName: PointLineUtil
 * @Description: 
 * @author gl
 * @date 2017年2月10日 下午5:59:31
 * 
 */
public class PointLineUtil {

    /**
     * @Title: distanceFromPointToLine
     * @Description: 点到直线的垂直距离，点坐标(x0, y0),线段端点坐标为(x1, y1)， (x2, y2)
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 距离
     */
    public static Double distanceFromPointToLine(Double x0, Double y0, Double x1, Double y1, Double x2, Double y2) {
        double fenzi = (y1 - y2) * x0 + (x1 - x2) * y0 + y1 * x2 - y2 * x1;
        double fenmu = Math.sqrt(Math.pow((y2 - y1), 2) + Math.pow((x2 - x1), 2));
        double result = Math.abs(fenzi / fenmu);
        return result;
    }

    /**
     * @Title: distanceFromPointToLineSegment
     * @Description: 点(x0, y0)到线段(x1,y1), (x2,
     *               y2)的距离。若理论垂点在线段上则返回点到直线的距离，否则返回改点到线段两端点距离的较小者
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static Double distanceFromPointToLineSegment(Double x0, Double y0, Double x1, Double y1, Double x2,
            Double y2) {
        Double distance = null;
        // 理论垂点
        Double[] verticalPoint = verticalPointFromPointToLine(x0, y0, x1, y1, x2, y2);
        // 理论垂点是否在线段上
        boolean isVerticalPointOnLineSegment = false;
        if (Math.min(x1, x2) <= verticalPoint[0] && verticalPoint[0] <= Math.max(x1, x2)
                && Math.min(y1, y2) <= verticalPoint[1] && verticalPoint[1] <= Math.max(y1, y2)) {
            isVerticalPointOnLineSegment = true;
        }
        // 若理论垂点在线段上则返回点到直线的距离，否则返回改点到线段两端点距离的较小者
        if (isVerticalPointOnLineSegment) {
            distance = distanceFromPointToLine(x0, y0, x1, y1, x2, y2);
        } else {
            Double d1 = distanceFromPointToPoint(x0, y0, x1, y1);
            Double d2 = distanceFromPointToPoint(x0, y0, x2, y2);
            distance = Math.min(d1, d2);
        }
        return distance;
    }

    /**
     * @Title: distanceFromPointToPoint
     * @Description: 点到点的距离，两点坐标为:(x1, y1), (x2, y2)
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 距离
     */
    public static Double distanceFromPointToPoint(Double x1, Double y1, Double x2, Double y2) {
        Double result = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        return result;
    }

    /**
     * @Title: verticalPointFromPointToLine
     * @Description: 点到直线的锤点，其中点坐标为(x0, y0)，直线所在线段两端点坐标分别为（x1, y1）, (x2, y2)
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 二维Double类型数组，表示垂点坐标
     */
    public static Double[] verticalPointFromPointToLine(Double x0, Double y0, Double x1, Double y1, Double x2,
            Double y2) {
        Double[] result = new Double[2];
        Double x = (Math.pow((x1 - x2), 2) * x0 + (x1 - x2) * (y1 - y2) * y0 + (y1 - y2) * (y1 * x2 - y2 * x1))
                / (Math.pow((y1 - y2), 2) + Math.pow((x1 - x2), 2));
        result[0] = x;
        Double y = (Math.pow((y1 - y2), 2) * y0 + (x1 - x2) * (y1 - y2) * x0 - (x1 - x2) * (y1 * x2 - y2 * x1))
                / (Math.pow((y1 - y2), 2) + Math.pow((x1 - x2), 2));
        result[1] = y;
        return result;
    }

    /**
     * @Title: wetherPointInCircleArea
     * @Description: 判断某点(x0, y0)是否在以(centerX, centerY)为圆心，redius为半径的区域里（含边界）
     * @param x0
     * @param y0
     * @param centerX
     * @param centerY
     * @param radius
     * @return
     */
    public static boolean wetherPointInCircleArea(Double x0, Double y0, Double centerX, Double centerY, Double radius) {
        Double distanceAwayFromCenter = distanceFromPointToPoint(y0, y0, centerX, centerY);
        if (distanceAwayFromCenter <= radius) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Title: closetPointFromPointToLine
     * @Description: 若垂点在路上则最近点是垂点，若不然这是两个端点中交近的那个 ，两个端点为(x1, y1), (x2, y2)
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static Double[] closetPointFromPointToLine(Double x0, Double y0, Double x1, Double y1, Double x2,
            Double y2) {
        Double[] verticlePoint = verticalPointFromPointToLine(x0, y0, x1, y1, x2, y2);
        if (Math.min(x1, x2) <= verticlePoint[0] && verticlePoint[0] <= Math.max(x1, x2)
                && Math.min(y1, y2) <= verticlePoint[1] && verticlePoint[1] <= Math.max(y1, y2)) {
            return verticlePoint;
        }
        Double[] firstNode = { x1, y1 };
        Double[] secondNode = { x2, y2 };
        Double distanceToFistNode = distanceFromPointToPoint(x0, y0, firstNode[0], firstNode[1]);
        Double distanceToSecondNode = distanceFromPointToPoint(x0, y0, secondNode[0], secondNode[1]);
        if (distanceToFistNode <= distanceToSecondNode) {
            return firstNode;
        } else {
            return secondNode;
        }

    }

    /**
     * @Title: getTheClosestRoad
     * @Description: 获取距离(x0, y0)最近的一条路
     * @param x0
     * @param y0
     * @param roads
     *            路径集合，其元素类型为Double[]，表示一条路（两个点）,依次为(firstNodeX,firstNodeY,
     *            secondNodeX, secondNodeY)
     * @return
     */
    public static Double[] getTheClosestRoad(Double x0, Double y0, List<Double[]> roads) {
        Double minDistance = Double.MAX_VALUE;
        Double[] closestRoad = new Double[4];
        Double temp = null;
        for (Double[] road : roads) {
            temp = distanceFromPointToLineSegment(x0, y0, road[0], road[1], road[2], road[3]);
            if (temp < minDistance) {
                minDistance = temp;
                closestRoad[0] = road[0];
                closestRoad[1] = road[1];
                closestRoad[2] = road[2];
                closestRoad[3] = road[3];
            }
        }
        return closestRoad;
    }

}
