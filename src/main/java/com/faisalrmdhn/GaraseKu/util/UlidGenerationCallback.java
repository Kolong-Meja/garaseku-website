package com.faisalrmdhn.GaraseKu.util;

import java.lang.reflect.Field;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class UlidGenerationCallback implements BeforeConvertCallback<Object> {
  private final IdGenerator idGenerator;

  public UlidGenerationCallback(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  @Override
  public Object onBeforeConvert(Object aggregate) {
    ReflectionUtils.doWithFields(aggregate.getClass(), field -> generateIfNeeded(aggregate, field));
    return aggregate;
  }

  private void generateIfNeeded(Object aggregate, Field field) {
    if (!field.isAnnotationPresent(Ulid.class)) {
      return;
    }
    if (!field.getType().equals(String.class)) {
      throw new IllegalStateException(
          "@Ulid can only be applied to String fields. Offending field: "
              + field.getDeclaringClass().getName() + "#" + field.getName());
    }

    ReflectionUtils.makeAccessible(field);
    Object currentValue = ReflectionUtils.getField(field, aggregate);
    if (currentValue == null) {
      ReflectionUtils.setField(field, aggregate, idGenerator.generate());
    }
  }
}
