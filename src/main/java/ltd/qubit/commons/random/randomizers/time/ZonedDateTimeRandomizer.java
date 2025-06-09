////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.randomizers.AbstractRandomizer;

/**
 * 生成随机{@link ZonedDateTime}的{@link Randomizer}。
 *
 * @author 胡海星
 */
public class ZonedDateTimeRandomizer extends AbstractRandomizer<ZonedDateTime> {

  private LocalDateTimeRandomizer localDateTimeRandomizer;

  /**
   * 创建一个新的{@link ZonedDateTimeRandomizer}。
   */
  public ZonedDateTimeRandomizer() {
    localDateTimeRandomizer = new LocalDateTimeRandomizer();
  }

  /**
   * 创建一个新的{@link ZonedDateTimeRandomizer}。
   *
   * @param seed
   *         初始种子
   */
  public ZonedDateTimeRandomizer(final long seed) {
    super(seed);
    localDateTimeRandomizer = new LocalDateTimeRandomizer(seed);
  }

  /**
   * 生成一个随机的带时区的日期时间。
   *
   * @return 一个随机的{@link ZonedDateTime}
   */
  @Override
  public ZonedDateTime getRandomValue() {
    final LocalDateTime randomLocalDateTime = localDateTimeRandomizer.getRandomValue();
    final ZoneId randomZoneId = getRandomZoneId();
    return ZonedDateTime.of(randomLocalDateTime, randomZoneId);
  }

  private ZoneId getRandomZoneId() {
    final Set<String> availableZoneIds = ZoneOffset.getAvailableZoneIds();
    final List<String> ids = new ArrayList<>(availableZoneIds);
    return ZoneId.of(ids.get(random.nextInt(ids.size())));
  }

  /**
   * 设置{@link LocalDateTimeRandomizer}。
   *
   * @param localDateTimeRandomizer
   *         要设置的{@link LocalDateTimeRandomizer}
   */
  public void setLocalDateTimeRandomizer(final LocalDateTimeRandomizer localDateTimeRandomizer) {
    this.localDateTimeRandomizer = localDateTimeRandomizer;
  }
}
