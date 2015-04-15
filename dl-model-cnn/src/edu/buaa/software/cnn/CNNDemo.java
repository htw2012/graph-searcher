package edu.buaa.software.cnn;

import edu.buaa.software.cnn.CNN.LayerBuilder;
import edu.buaa.software.cnn.Layer.Size;
import edu.buaa.software.dataset.Dataset;
import edu.buaa.software.util.ConcurenceRunner;
import edu.buaa.software.util.TimedTest;
import edu.buaa.software.util.TimedTest.TestTask;

public class CNNDemo {

    public static void runCnn() {
        // 创建一个卷积神经网络
        LayerBuilder builder = new LayerBuilder();
        builder.addLayer(Layer.buildInputLayer(new Size(28, 28)));
        builder.addLayer(Layer.buildConvLayer(6, new Size(5, 5)));
        builder.addLayer(Layer.buildSampLayer(new Size(2, 2)));
        builder.addLayer(Layer.buildConvLayer(12, new Size(5, 5)));
        builder.addLayer(Layer.buildSampLayer(new Size(2, 2)));
        builder.addLayer(Layer.buildOutputLayer(10));
        CNN cnn = new CNN(builder, 50);

        // 导入数据集
        String fileName = "dataset/train.format";
        Dataset dataset = Dataset.load(fileName, ",", 784);
        cnn.train(dataset, 200);//训练次数
        String modelName = "model/model.cnn";
        cnn.saveModel(modelName);
        dataset.clear();
        dataset = null;

        // 预测
        // CNN cnn = CNN.loadModel(modelName);
        Dataset testset = Dataset.load("dataset/test.format", ",", -1);
        cnn.predict(testset, "dataset/test.predict");
    }

    public static void main(String[] args) {

        new TimedTest(new TestTask() {

            @Override
            public void process() {
                runCnn();
            }
        }, 1).test();
        ConcurenceRunner.stop();

    }

}
