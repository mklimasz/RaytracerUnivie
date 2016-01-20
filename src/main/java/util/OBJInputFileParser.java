package util;

import model.Vector3D;
import model.shape.impl.Triangle;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OBJInputFileParser {
    private OBJInputFileParser() {
        throw new AssertionError();
    }

    public static List<Triangle> parseOBJ(String path) throws IOException {
        List<Triangle> triangleList = new ArrayList<>();
        InputStream inputStream = new FileInputStream(path);
        List<Vector3D> vertices = new ArrayList<>();
        List<Vector3D> normals = new ArrayList<>();
        List<String> lines = Arrays.asList(IOUtils.toString(inputStream, Charset.defaultCharset()).split("\\r?\\n"));
        lines.stream()
                .filter(l -> new Character('v').equals(l.charAt(0)))
                .forEach(l -> parseVerticesLine(l, vertices, normals));
        normals.forEach(n -> {
            Triangle t1 = new Triangle();
            t1.setNormal(n);
            Triangle t2 = new Triangle();
            t2.setNormal(n);
            triangleList.add(t1);
            triangleList.add(t2);
        });
        for (int i = 0, j = 0; i < triangleList.size(); i++) {
            triangleList.get(i++).setVertices(Arrays.asList(
                    vertices.get(j++), vertices.get(j++), vertices.get(j--)
            ));
            j--;
            Vector3D tmp = vertices.get(j++);
            j++;
            triangleList.get(i).setVertices(Arrays.asList(
                    tmp, vertices.get(j++), vertices.get(j++)
            ));
        }
        return triangleList;
    }

    private static void parseVerticesLine(String line, List<Vector3D> vertices, List<Vector3D> normals) {
        if(new Character('t').equals(line.charAt(1))) {}
        else if(new Character('n').equals(line.charAt(1))) {
            String[] split = line.split(" ");
            normals.add(new Vector3D(
                    Double.valueOf(split[1]),
                    Double.valueOf(split[2]),
                    Double.valueOf(split[3])
            ));
        }
        else {
            String[] split = line.split(" ");
            vertices.add(new Vector3D(
                    Double.valueOf(split[1]),
                    Double.valueOf(split[2]),
                    Double.valueOf(split[3])
            ));
        }
    }


}
