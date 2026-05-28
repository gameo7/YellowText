/*
 * Copyright (C) 2024 MisterCheezeCake
 *
 * This file is part of YellowText.
 *
 * YellowText is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * YellowText is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with YellowText. If not, see <https://www.gnu.org/licenses/>.
 */
package wtf.cheeze.yellowtext.mixin;

import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbstractButton.class)
public abstract class PressableWidgetMixin extends ClickableWidgetMixin {

	@ModifyArg(method = "extractDefaultLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractButton;extractScrollingStringOverContents(Lnet/minecraft/client/gui/ActiveTextCollector;Lnet/minecraft/network/chat/Component;I)V"), index = 1)
	private Component modifyTextColor(Component text) {
		int baseColor = this.active ? (this.isHovered ? 0xFFFFA0 : 0xFFFFFF) : 0xA0A0A0; // Pre-1.15 inactive color is 0xE0E0E0
		int colorWithAlpha = baseColor | (Mth.ceil(this.alpha * 255.0F) << 24);

		return ComponentUtils.mergeStyles(text, Style.EMPTY.withColor(colorWithAlpha));
	}

}