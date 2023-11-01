import React, { useState, useRef, useEffect, useCallback } from 'react'
import style from './index.module.scss'
import videojs from "video.js"
import "video.js/dist/video-js.css"
import { type IVideo } from '../../libs/model'

interface Props {
  propsOption: IVideo
  videoUrl: string
  hoverFunc?: boolean
}

export default function VideoComponent({ propsOption, videoUrl, hoverFunc }: Props) {
  const videoRef = useRef(null)
  const playerRef = useRef<any>(null)

  const onReadyPlay = (player: any) => {
    videoRef.current = player
    player.play()
  }

  useEffect(() => {
    if (!playerRef.current) {
      const videoElement = videoRef.current
      if (!videoElement) return
      videojs.addLanguage('zh-CN', require('video.js/dist/lang/zh-CN.json'))
      const player = playerRef.current = videojs(videoElement, propsOption, () => {
      })
      if (hoverFunc) {
        hoverFunc && player.on('mouseover', () => player.play())
        hoverFunc && player.on('mouseout', () => player.pause())
      }
      onReadyPlay(player)
    }

    return () => {
      playerRef.current.pause()
    }
  }, [])
  return (
    <div className={style.video_div}>
      <video style={{
        width: '100%',
        height: '100%'
      }} ref={videoRef}
        className="video-js vjs-big-play-centered">
        <source src={videoUrl} type="video/mp4" />
      </video>
    </div>
  )
}
