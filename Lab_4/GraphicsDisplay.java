package bsu.rfe.java.group5.lab4.Matsuk.varb4;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.*;

public class GraphicsDisplay extends JPanel {
    // Список координат точек для построения графика
    private Double[][] graphicsData;
    // Флаговые переменные, задающие правила отображения графика
    private boolean showAxis = true;
    private boolean showMarkers = true;
    ///////////////////////////////
    private boolean showSpecialMarkers = true;
    // Границы диапазона пространства, подлежащего отображению
    private double min_x;
    private double max_x;
    private double min_y;
    private double max_y;
    // Используемый масштаб отображения
    private double scale;
    // Различные стили черчения линий
    private BasicStroke graphics_stroke;
    private BasicStroke axis_stroke;
    private BasicStroke marker_stroke;
    // Различные шрифты отображения надписей
    private Font axisFont;

    public GraphicsDisplay() {
// Цвет заднего фона области отображения - белый
        setBackground(Color.WHITE);

        float[] dashPattern = {
                26.0f, 4.0f,
                10.0f, 4.0f,
                10.0f, 4.0f,
                4.0f, 4.0f,
                26.0f, 4.0f,
        };
        graphics_stroke = new BasicStroke(
                3.0f, // ширина линии
                BasicStroke.CAP_BUTT,  // линии имеют плоские концы
                BasicStroke.JOIN_ROUND, // значает, что точки, где встречаются две линии, будут скруглены.
                10.0f,
                dashPattern, //Это массив, определяющий шаблон пунктирной линии. Если он null, линия будет сплошной.
                0.0f
        );

// Сконструировать необходимые объекты, используемые в рисовании
// Перо для рисования графика
       /* graphicsStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 10.0f, null, 0.0f);*/
// Перо для рисования осей координат
        axis_stroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);
// Перо для рисования контуров маркеров
        marker_stroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);
// Шрифт для подписей осей координат
        axisFont = new Font("Serif", Font.BOLD, 36);
    }

    // Данный метод вызывается из обработчика элемента меню "Открыть файл с графиком"
    // главного окна приложения в случае успешной загрузки данных
    public void showGraphics(Double[][] graphicsData) {
// Сохранить массив точек во внутреннем поле класса
        this.graphicsData = graphicsData;
// Запросить перерисовку компонента, т.е. неявно вызвать paintComponent()
        repaint();
    }

    // Методы-модификаторы для изменения параметров отображения графика
// Изменение любого параметра приводит к перерисовке области
    public void setShowAxis(boolean showAxis) {
        this.showAxis = showAxis;
        repaint();
    }

    public void setShowMarkers(boolean showMarkers) {
        this.showMarkers = showMarkers;
        repaint();
    }

    ///////////////////////////////////
    public void setShowSpecialMarkers(boolean showSpecialMarkers) {
        this.showSpecialMarkers = showSpecialMarkers;
        repaint();
    }

    // Метод отображения всего компонента, содержащего график
    public void paintComponent(Graphics g) {
        /* Шаг 1 - Вызвать метод предка для заливки области цветом заднего фона
         * Эта функциональность - единственное, что осталось в наследство от
         * paintComponent класса JPanel
         */
        super.paintComponent(g);
// Шаг 2 - Если данные графика не загружены (при показе компонента при запуске программы) - ничего не делать
        if (graphicsData == null || graphicsData.length == 0) return;
// Шаг 3 - Определить минимальное и максимальное значения для координат X и Y
// Это необходимо для определения области пространства, подлежащей отображению
// Еѐ верхний левый угол это (minX, maxY) - правый нижний это (maxX, minY)
        min_x = graphicsData[0][0];
        max_x = graphicsData[graphicsData.length - 1][0];
        min_y = graphicsData[0][1];
        max_y = min_y;
// Найти минимальное и максимальное значение функции
        for (int i = 1; i < graphicsData.length; i++) {
            if (graphicsData[i][1] < min_y) {
                min_y = graphicsData[i][1];
            }
            if (graphicsData[i][1] > max_y) {
                max_y = graphicsData[i][1];
            }
        }
/* Шаг 4 - Определить (исходя из размеров окна) масштабы по осям X
и Y - сколько пикселов
* приходится на единицу длины по X и по Y
*/
        double scaleX = getSize().getWidth() / (max_x - min_x);
        double scaleY = getSize().getHeight() / (max_y - min_y);
// Шаг 5 - Чтобы изображение было неискажѐнным - масштаб должен быть одинаков
// Выбираем за основу минимальный
        scale = Math.min(scaleX, scaleY);
// Шаг 6 - корректировка границ отображаемой области согласно выбранному масштабу
        if (scale == scaleX) {
/* Если за основу был взят масштаб по оси X, значит по оси Y
делений меньше,
* т.е. подлежащий визуализации диапазон по Y будет меньше
высоты окна.
* Значит необходимо добавить делений, сделаем это так:
* 1) Вычислим, сколько делений влезет по Y при выбранном
масштабе - getSize().getHeight()/scale
* 2) Вычтем из этого сколько делений требовалось изначально
* 3) Набросим по половине недостающего расстояния на maxY и
minY
*/
            double yIncrement = (getSize().getHeight() / scale - (max_y - min_y)) / 2;
            max_y += yIncrement;
            min_y -= yIncrement;
        }
        if (scale == scaleY) {
// Если за основу был взят масштаб по оси Y, действовать по аналогии
            double xIncrement = (getSize().getWidth() / scale - (max_x - min_x)) / 2;
            max_x += xIncrement;
            min_x -= xIncrement;
        }
// Шаг 7 - Сохранить текущие настройки холста
        Graphics2D canvas = (Graphics2D) g;
        Stroke oldStroke = canvas.getStroke();
        Color oldColor = canvas.getColor();
        Paint oldPaint = canvas.getPaint();
        Font oldFont = canvas.getFont();
// Шаг 8 - В нужном порядке вызвать методы отображения элементов графика
// Порядок вызова методов имеет значение, т.к. предыдущий рисунок будет затираться последующим
// Первыми (если нужно) отрисовываются оси координат.
        if (showAxis) paintAxis(canvas);
// Затем отображается сам график
        paintGraphics(canvas);
// Затем (если нужно) отображаются маркеры точек, по которым строился график.


        if (showMarkers) paintMarkers(canvas);
// Шаг 9 - Восстановить старые настройки холста
        canvas.setFont(oldFont);
        canvas.setPaint(oldPaint);
        canvas.setColor(oldColor);
        canvas.setStroke(oldStroke);



        // ОТОБРАЖЕНИЕ СПЕЦИАЛЬНЫХ ТОЧЕК
        if (showSpecialMarkers) paintSpecialMarkers(canvas);
        canvas.setFont(oldFont);
        canvas.setPaint(oldPaint);
        canvas.setColor(oldColor);
        canvas.setStroke(oldStroke);
    }

    // Отрисовка графика по прочитанным координатам
    protected void paintGraphics(Graphics2D canvas) {
// Выбрать линию для рисования графика
        canvas.setStroke(graphics_stroke);
// Выбрать цвет линии
        canvas.setColor(Color.RED);
/* Будем рисовать линию графика как путь, состоящий из множества
сегментов (GeneralPath)
* Начало пути устанавливается в первую точку графика, после чего
прямой соединяется со
* следующими точками
*/
        GeneralPath graphics = new GeneralPath();
        for (int i = 0; i < graphicsData.length; i++) {
// Преобразовать значения (x,y) в точку на экране point
            Point2D.Double point = xyToPoint(graphicsData[i][0],
                    graphicsData[i][1]);
            if (i > 0) {
// Не первая итерация цикла - вести линию в точку point
                graphics.lineTo(point.getX(), point.getY());
            } else {
// Первая итерация цикла - установить начало пути в точку point
                graphics.moveTo(point.getX(), point.getY());
            }
        }
// Отобразить график
        canvas.draw(graphics);
    }


    private Point2D.Double constructPoint(
            Point2D.Double point,
            double dx,
            double dy
    ) {
        Point2D.Double dest = new Point2D.Double();
        dest.setLocation(
                point.getX() + dx,
                point.getY() + dy
        );
        return dest;
    }


    // ОТРИСОВКА ОСОБЫХ ТОЧЕК
    protected void paintSpecialMarkers(Graphics2D canvas) {
        canvas.setStroke(marker_stroke);
        for (int i = 0; i < graphicsData.length; i++) {
            Double[] point = graphicsData[i];
            if (i + 1 < graphicsData.length && point[1] * graphicsData[i + 1][1] < 0) {
                double x = (Math.abs(point[1]) * graphicsData[i + 1][0] + Math.abs(graphicsData[i + 1][1]) * point[0]) / (Math.abs(point[1]) + Math.abs(graphicsData[i + 1][1]));
                var targetPoint = xyToPoint (x, 0);
                canvas.draw(new Ellipse2D.Double(targetPoint.getX() - 10, targetPoint.getY() - 10, 20, 20));
            }
        }
    }



    public boolean isOdd(int n) {
        return (n & 1) == 1;
    }

    // Отображение маркеров точек, по которым рисовался график
    protected void paintMarkers(Graphics2D canvas) {
// Шаг 1 - Установить специальное перо для черчения контуров
//        маркеров
        canvas.setStroke(marker_stroke);

// Шаг 2 - Организовать цикл по всем точкам графика

        for (int i = 0; i < graphicsData.length; i++) {
            Double[] point = graphicsData[i];

/* Эллипс будет задаваться посредством указания координат
его центра
и угла прямоугольника, в который он вписан */
// Центр - в точке (x,y)

// Задать эллипс по центру и диагонали
            canvas.setColor(Color.BLACK);
            if (isOdd((int) Math.floor(point[1]))) {
//                System.out.`println(Arrays.toString(point));
                canvas.setColor(Color.YELLOW);
            }


            Line2D.Double horizontalLine = new Line2D.Double();
            horizontalLine.setLine(

                    constructPoint(xyToPoint(point[0], point[1]), 5, 0),
                    constructPoint(xyToPoint(point[0], point[1]), -5, 0)
            );

            Line2D.Double verticalLine = new Line2D.Double();
            verticalLine.setLine(
                    constructPoint(xyToPoint(point[0], point[1]), 0, 5),
                    constructPoint(xyToPoint(point[0], point[1]), 0, -5)
            );

            Line2D.Double horizontalThorn1 = new Line2D.Double();
            horizontalThorn1.setLine(

                    constructPoint(xyToPoint(point[0], point[1]), -5, 2),
                    constructPoint(xyToPoint(point[0], point[1]), -5, -2)
            );

            Line2D.Double verticalThorn1 = new Line2D.Double();
            verticalThorn1.setLine(
                    constructPoint(xyToPoint(point[0], point[1]), 2, -5),
                    constructPoint(xyToPoint(point[0], point[1]), -2, -5)
            );

            Line2D.Double horizontalThorn2 = new Line2D.Double();
            horizontalThorn2.setLine(

                    constructPoint(xyToPoint(point[0], point[1]), 5, -2),
                    constructPoint(xyToPoint(point[0], point[1]), 5, 2)
            );

            Line2D.Double verticalThorn2 = new Line2D.Double();
            verticalThorn2.setLine(
                    constructPoint(xyToPoint(point[0], point[1]), 2, 5),
                    constructPoint(xyToPoint(point[0], point[1]), -2, 5)
            );

            canvas.draw(verticalLine);
            canvas.draw(horizontalLine);
            canvas.draw(horizontalThorn1);
            canvas.draw(verticalThorn1);
            canvas.draw(horizontalThorn2);
            canvas.draw(verticalThorn2);
        }
    }


    // Метод, обеспечивающий отображение осей координат
    protected void paintAxis(Graphics2D canvas) {
// Установить особое начертание для осей
        canvas.setStroke(axis_stroke);
// Оси рисуются чѐрным цветом
        canvas.setColor(Color.BLACK);
// Стрелки заливаются чѐрным цветом
        canvas.setPaint(Color.BLACK);
// Подписи к координатным осям делаются специальным шрифтом
        canvas.setFont(axisFont);
// Создать объект контекста отображения текста - для получения характеристик устройства (экрана)
        FontRenderContext context = canvas.getFontRenderContext();
// Определить, должна ли быть видна ось Y на графике
        if (min_x <= 0.0 && max_x >= 0.0) {
// Она должна быть видна, если левая граница показываемой области (minX) <= 0.0,
// а правая (maxX) >= 0.0
// Сама ось - это линия между точками (0, maxY) и (0, minY)
            canvas.draw(new Line2D.Double(xyToPoint(0, max_y),
                    xyToPoint(0, min_y)));
// Стрелка оси Y
            GeneralPath arrow = new GeneralPath();
// Установить начальную точку ломаной точно на верхний конец оси Y
            Point2D.Double lineEnd = xyToPoint(0, max_y);
            arrow.moveTo(lineEnd.getX(), lineEnd.getY());
// Вести левый "скат" стрелки в точку с относительными координатами (5,20)
            arrow.lineTo(arrow.getCurrentPoint().getX() + 5,
                    arrow.getCurrentPoint().getY() + 20);
// Вести нижнюю часть стрелки в точку с относительными координатами (-10, 0)
            arrow.lineTo(arrow.getCurrentPoint().getX() - 10,
                    arrow.getCurrentPoint().getY());
// Замкнуть треугольник стрелки
            arrow.closePath();
            canvas.draw(arrow); // Нарисовать стрелку
            canvas.fill(arrow); // Закрасить стрелку
// Нарисовать подпись к оси Y
// Определить, сколько места понадобится для надписи "y"
            Rectangle2D bounds = axisFont.getStringBounds("y", context);
            Point2D.Double labelPos = xyToPoint(0, max_y);
// Вывести надпись в точке с вычисленными координатами
            canvas.drawString("y", (float) labelPos.getX() + 10,
                    (float) (labelPos.getY() - bounds.getY()));
        }
// Определить, должна ли быть видна ось X на графике
        if (min_y <= 0.0 && max_y >= 0.0) {
// Она должна быть видна, если верхняя граница показываемой области (maxX) >= 0.0,
// а нижняя (minY) <= 0.0
            canvas.draw(new Line2D.Double(xyToPoint(min_x, 0),
                    xyToPoint(max_x, 0)));
// Стрелка оси X
            GeneralPath arrow = new GeneralPath();
// Установить начальную точку ломаной точно на правый конец оси X
            Point2D.Double lineEnd = xyToPoint(max_x, 0);
            arrow.moveTo(lineEnd.getX(), lineEnd.getY());
// Вести верхний "скат" стрелки в точку с относительными координатами (-20,-5)
            arrow.lineTo(arrow.getCurrentPoint().getX() - 20,
                    arrow.getCurrentPoint().getY() - 5);
// Вести левую часть стрелки в точку с относительными координатами (0, 10)
            arrow.lineTo(arrow.getCurrentPoint().getX(),
                    arrow.getCurrentPoint().getY() + 10);
// Замкнуть треугольник стрелки
            arrow.closePath();
            canvas.draw(arrow); // Нарисовать стрелку
            canvas.fill(arrow); // Закрасить стрелку
// Нарисовать подпись к оси X
// Определить, сколько места понадобится для надписи "x"
            Rectangle2D bounds = axisFont.getStringBounds("x", context);
            Point2D.Double labelPos = xyToPoint(max_x, 0);
// Вывести надпись в точке с вычисленными координатами
            canvas.drawString("x", (float) (labelPos.getX() -
                    bounds.getWidth() - 10), (float) (labelPos.getY() + bounds.getY()));
        }
    }

    /* Метод-помощник, осуществляющий преобразование координат.
    * Оно необходимо, т.к. верхнему левому углу холста с координатами
    * (0.0, 0.0) соответствует точка графика с координатами (minX, maxY),
    где
    * minX - это самое "левое" значение X, а
    * maxY - самое "верхнее" значение Y.
    */
    protected Point2D.Double xyToPoint(double x, double y) {
// Вычисляем смещение X от самой левой точки (minX)
        double deltaX = x - min_x;
// Вычисляем смещение Y от точки верхней точки (maxY)
        double deltaY = max_y - y;
        return new Point2D.Double(deltaX * scale, deltaY * scale);
    }

    /* Метод-помощник, возвращающий экземпляр класса Point2D.Double
     * смещѐнный по отношению к исходному на deltaX, deltaY
     * К сожалению, стандартного метода, выполняющего такую задачу, нет.
     */
    protected Point2D.Double shiftPoint(Point2D.Double src, double deltaX,
                                        double deltaY) {
// Инициализировать новый экземпляр точки
        Point2D.Double dest = new Point2D.Double();
// Задать еѐ координаты как координаты существующей точки + заданные смещения
        dest.setLocation(src.getX() + deltaX, src.getY() + deltaY);
        return dest;
    }
}