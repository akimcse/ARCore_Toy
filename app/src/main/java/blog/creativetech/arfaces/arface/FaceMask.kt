package blog.creativetech.arfaces.arface

data class FaceMaskElement(val landmark: AugmentedFaceNode.Companion.FaceLandmark,
                           val model: String,
                           val texture: String)

data class FaceMask(val faceTexture: String?,
                    val landmarkMap: ArrayList<FaceMaskElement>?)
