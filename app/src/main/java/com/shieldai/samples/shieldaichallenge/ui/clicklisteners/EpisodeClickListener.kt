package com.shieldai.samples.shieldaichallenge.ui.clicklisteners

import com.shieldai.samples.shieldaichallenge.data.models.Episode

class EpisodeClickListener(val clickListener: (episode: Episode, position: Int) -> Unit) {
  fun onClick(episode: Episode, position: Int) = clickListener(episode, position)
}