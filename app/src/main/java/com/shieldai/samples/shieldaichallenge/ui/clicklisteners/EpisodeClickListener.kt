package com.shieldai.samples.shieldaichallenge.ui.clicklisteners

import com.shieldai.samples.shieldaichallenge.data.models.Episode

class EpisodeClickListener(val clickListener: (episode: Episode) -> Unit) {
  fun onClick(episode: Episode) = clickListener(episode)
}