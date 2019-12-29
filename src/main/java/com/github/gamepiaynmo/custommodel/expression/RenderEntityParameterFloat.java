package com.github.gamepiaynmo.custommodel.expression;

import com.github.gamepiaynmo.custommodel.client.CustomModelClient;
import com.github.gamepiaynmo.custommodel.render.RenderContext;
import com.github.gamepiaynmo.custommodel.render.RenderParameter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

import java.util.function.BiFunction;

public enum RenderEntityParameterFloat implements IExpressionFloat {
   LIMB_SWING("limb_swing", (entity, params) -> params.limbSwing),
   LIMB_SWING_SPEED("limb_speed", (entity, params) -> params.limbSwingAmount),
   AGE("age", (entity, params) -> params.age),
   HEAD_YAW("head_yaw", (entity, params) -> params.headYaw),
   HEAD_PITCH("head_pitch", (entity, params) -> params.headPitch),
   SCALE("scale", (entity, params) -> params.scale),
   HEALTH("health", (entity, params) -> entity.getHealth()),
   FOOD_LEVEL("food_level", (entity, params) -> (float) (entity instanceof PlayerEntity ? ((PlayerEntity) entity).getHungerManager().getFoodLevel() : 0)),
   HURT_TIME("hurt_time", (entity, params) -> (float) entity.hurtTime - params.partial),
   POS_X("pos_x", (entity, params) -> (float) MathHelper.lerp(params.partial, entity.prevX, entity.x)),
   POS_Y("pos_y", (entity, params) -> (float) MathHelper.lerp(params.partial, entity.prevY, entity.y)),
   POS_Z("pos_z", (entity, params) -> (float) MathHelper.lerp(params.partial, entity.prevZ, entity.z)),
   SPEED_X("speed_x", (entity, params) -> (float) entity.getVelocity().x),
   SPEED_Y("speed_y", (entity, params) -> (float) entity.getVelocity().y),
   SPEED_Z("speed_z", (entity, params) -> (float) entity.getVelocity().z),
   YAW("yaw", (entity, params) -> MathHelper.lerp(params.partial, entity.prevYaw, entity.yaw)),
   BODY_YAW("body_yaw", (entity, params) -> MathHelper.lerp(params.partial, entity.field_6220, entity.field_6283)),
   PITCH("pitch", (entity, params) -> MathHelper.lerp(params.partial, entity.prevPitch, entity.pitch)),
   SWING_PROGRESS("swing_progress", (entity, params) -> entity.getHandSwingProgress(params.partial)),
   CURRENT_POSE("current_pose", (entity, params) -> (float) ModelResolver.poseId.get(entity.getPose()));

   private String name;
   private static final RenderEntityParameterFloat[] VALUES = values();
   private final BiFunction<LivingEntity, RenderParameter, Float> valueGetter;

   RenderEntityParameterFloat(String name, BiFunction<LivingEntity, RenderParameter, Float> getter) {
      this.name = name;
      valueGetter = getter;
   }

   public String getName() {
      return this.name;
   }

   public float eval(RenderContext context) {
      return valueGetter.apply(context.currentEntity, context.currentParameter);
   }

   public static RenderEntityParameterFloat parse(String str) {
      if (str == null) {
         return null;
      } else {
         for (RenderEntityParameterFloat type : VALUES) {
            if (type.getName().equals(str)) {
               return type;
            }
         }

         return null;
      }
   }
}
