package br.com.shapeup.core.domain.stories;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import java.util.Objects;

public class Stories extends Entity<StoriesId> {
    private String pictureStorieUrl;

    public Stories(StoriesId id, String pictureStorieUrl) {
        super(id);
        this.pictureStorieUrl = pictureStorieUrl;
    }
    public static Stories newStories(StoriesId id, String pictureStorieUrl){
        return new Stories(id, pictureStorieUrl);
    }

    @Override
    public void validate(ValidationHandler handler) {
        // new StoriesValidator(handler, this).validate();
    }

    public String getPictureStorieUrl() {
        return pictureStorieUrl;
    }

    public void setPictureStorieUrl(String pictureStorieUrl) {
        this.pictureStorieUrl = pictureStorieUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Stories stories = (Stories) o;

        return Objects.equals(pictureStorieUrl, stories.pictureStorieUrl);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (pictureStorieUrl != null ? pictureStorieUrl.hashCode() : 0);
        return result;
    }
}
