package kr.mapo.eco100.controller.v1.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.mapo.eco100.error.ImageNotFoundException;

@Api(value = "이미지 컨트롤러")
@RestController
public class ImageController {

    @ApiOperation(value = "이미지 URL GET 요청")
    @GetMapping(value="/image/{kind}/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> showImage(@PathVariable String kind, @PathVariable String filename) throws IOException {
        InputStream imageStream = null;
        byte[] imageByteArray = null;
        try {
            imageStream = new FileInputStream("./images/" + kind + "/" + filename);
            imageByteArray = IOUtils.toByteArray(imageStream);
        } catch (FileNotFoundException e) {
            throw new ImageNotFoundException("이미지가 존재하지 않음");
        } finally {
            imageStream.close();
        }
        return ResponseEntity.ok(imageByteArray); 
    }

}
