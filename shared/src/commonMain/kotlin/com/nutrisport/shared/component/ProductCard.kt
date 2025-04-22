package com.nutrisport.shared.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.nutrisport.shared.Alpha
import com.nutrisport.shared.BorderIdle
import com.nutrisport.shared.FontSize
import com.nutrisport.shared.Resources
import com.nutrisport.shared.RobotoCondensedFont
import com.nutrisport.shared.SurfaceLighter
import com.nutrisport.shared.TextPrimary
import com.nutrisport.shared.TextSecondary
import com.nutrisport.shared.domain.Product
import com.nutrisport.shared.domain.ProductCategory
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(size = 12.dp))
            .border(
                width = 1.dp,
                color = BorderIdle,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .background(SurfaceLighter)
            .clickable { onClick(product.id) }
    ) {
        AsyncImage(
            modifier = Modifier
                .width(120.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(size = 12.dp))
                .border(
                    width = 1.dp,
                    color = BorderIdle,
                    shape = RoundedCornerShape(size = 12.dp)
                ),
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(product.thumbnail)
                .crossfade(enable = true)
                .build(),
            contentDescription = "Product thumbnail image",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(all = 12.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = product.title,
                fontSize = FontSize.MEDIUM,
                color = TextPrimary,
                fontFamily = RobotoCondensedFont(),
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(Alpha.HALF),
                text = product.description,
                fontSize = FontSize.REGULAR,
                lineHeight = FontSize.REGULAR * 1.3,
                color = TextPrimary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AnimatedContent(
                    targetState = product.category
                ) { category ->
                    if (ProductCategory.valueOf(category) == ProductCategory.Accessories) {
                        Spacer(modifier = Modifier.weight(1f))
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(14.dp),
                                painter = painterResource(Resources.Icon.Weight),
                                contentDescription = "Weight icon"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${product.weight}g",
                                fontSize = FontSize.EXTRA_SMALL,
                                color = TextPrimary
                            )
                        }
                    }
                }
                Text(
                    text = "$${product.price}",
                    fontSize = FontSize.EXTRA_REGULAR,
                    color = TextSecondary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}